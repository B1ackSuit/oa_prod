package cn.ean.workflow.service.impl;

import cn.ean.workflow.actutils.image.CustomProcessDiagramGenerator;
import cn.ean.workflow.enums.BusinessStatusEnum;
import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.ProcInstREQ;
import cn.ean.workflow.model.dto.StartREQ;
import cn.ean.workflow.model.po.BusinessStatus;
import cn.ean.workflow.model.po.ProcessConfig;
import cn.ean.workflow.service.ActivitiService;
import cn.ean.workflow.service.IBusinessStatusService;
import cn.ean.workflow.service.IProcessConfigService;
import cn.ean.workflow.service.IProcessInstanceService;
import cn.ean.workflow.utils.DateUtils;
import cn.ean.workflow.utils.UserUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ean
 * @FileName ProcessInstanceServiceImpl
 * @Date 2022/5/25 12:25
 **/
@Service
public class ProcessInstanceServiceImpl extends ActivitiService implements IProcessInstanceService {

    @Autowired
    IProcessConfigService processConfigService;

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public Result startProcess(StartREQ req) {
        // 1. ????????????????????????????????????????????????????????????key??????????????????????????????????????????????????????
        ProcessConfig processConfig =
                processConfigService.getByBusinessRoute(req.getBusinessRoute());

        // 2. ??????????????????????????????????????????????????????????????????????????????
        Map<String, Object> variables = req.getVariables(); // ??????????????????????????????????????????entity: {??????????????????}}
        variables.put("formName", processConfig.getFormName());

        // ???????????????????????????????????????
        List<String> assignees = req.getAssignees();
        if(CollectionUtils.isEmpty(assignees)) {
            return Result.error("??????????????????");
        }

        // 3. ????????????????????????????????????
        Authentication.setAuthenticatedUserId(UserUtils.getUsername());
        ProcessInstance pi =
                runtimeService.startProcessInstanceByKey(processConfig.getProcessKey(),
                        req.getBusinessKey(), variables);

        // ????????????????????? ?????? ??????????????????
        runtimeService.setProcessInstanceName(pi.getProcessInstanceId(), pi.getProcessDefinitionName());

        // 4. ?????????????????????
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        for (Task task : taskList) {
            if(assignees.size() == 1) {
                // ?????????????????????????????????????????????????????????
                taskService.setAssignee(task.getId(), assignees.get(0));
            }else {
                // ???????????????????????????????????????
                for(String assignee: assignees) {
                    taskService.addCandidateUser(task.getId(), assignee);
                }
            }
        }

        // 5. ?????????????????????????????????, ???????????????id
        return businessStatusService.updateState(req.getBusinessKey(),
                BusinessStatusEnum.PROCESS,
                pi.getProcessInstanceId());
    }

    @Override
    public Result cancel(String businessKey, String procInstId, String message) {
        // 1. ????????????????????????
        runtimeService.deleteProcessInstance(procInstId,
                UserUtils.getUsername() + " ??????????????????????????????" + message);

        // 2. ??????????????????
        historyService.deleteHistoricProcessInstance(procInstId);
        historyService.deleteHistoricTaskInstance(procInstId);

        // 3. ??????????????????
        return businessStatusService.updateState(businessKey, BusinessStatusEnum.CANCEL, "");
    }

    @Override
    public Result getFormNameByProcInstId(String procInstId) {
        // ??????????????????id?????????????????????????????????????????????
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(procInstId).singleResult();

        return Result.ok(hpi.getProcessVariables().get("formName"));
    }

    @Override
    public Result getHistoryInfoList(String procInstId) {
        // ???????????????????????????????????????
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(procInstId)
                .orderByHistoricTaskInstanceStartTime()
                .asc()
                .list();

        List<Map<String, Object>> records = new ArrayList<>();
        for (HistoricTaskInstance hti : list) {
            Map<String, Object> result = new HashMap<>();
            result.put("taskId", hti.getId()); // ??????ID
            result.put("taskName", hti.getName()); // ????????????
            result.put("processInstanceId", hti.getProcessInstanceId()); //????????????ID
            result.put("startTime", DateUtils.format(hti.getStartTime())); // ????????????
            result.put("endTime", DateUtils.format(hti.getEndTime())); // ????????????
            result.put("status", hti.getEndTime() == null ? "?????????": "?????????"); // ??????
            result.put("assignee", hti.getAssignee()); // ?????????

            // ????????????
            String message = hti.getDeleteReason();
            if(StringUtils.isEmpty(message)) {
                List<Comment> taskComments = taskService.getTaskComments(hti.getId());
                message = taskComments.stream()
                        .map(m -> m.getFullMessage()).collect(Collectors.joining("???"));
            }
            result.put("message", message);

            records.add(result);
        }

        return Result.ok(records);
    }

    @Override
    public void getHistoryProcessImage(String prodInstId, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            // 1.??????????????????????????????
            HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(prodInstId).singleResult();

            // 2. ??????????????????????????????????????????????????????????????????
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(prodInstId)
                    .orderByHistoricActivityInstanceStartTime().desc()
                    .list();

            // 3. ???????????????????????????id ( ?????????
            List<String> highLightedActivityIdList =
                    historicActivityInstanceList.stream()
                            .map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());

            // 4. ????????????????????? ????????????
            List<Execution> runningActivityInstanceList = runtimeService.createExecutionQuery()
                    .processInstanceId(prodInstId).list();

            List<String> runningActivityIdList = new ArrayList<>();
            for (Execution execution : runningActivityInstanceList) {
                if(StringUtils.isNotEmpty(execution.getActivityId())) {
                    runningActivityIdList.add(execution.getActivityId());
                }
            }

            // ??????????????????Model??????
            BpmnModel bpmnModel = repositoryService.getBpmnModel(instance.getProcessDefinitionId());

            // ???????????????????????????
            CustomProcessDiagramGenerator generator = new CustomProcessDiagramGenerator();
            // ??????????????????id
            List<String> highLightedFlows = generator.getHighLightedFlows(bpmnModel, historicActivityInstanceList);
            // ?????????????????????
            inputStream = generator.generateDiagramCustom(bpmnModel, highLightedActivityIdList,
                    runningActivityIdList, highLightedFlows,
                    "??????", "????????????", "??????");

            // ??????????????????
            response.setContentType("image/svg+xml");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if( inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Result getProcInstRunning(ProcInstREQ req) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        if(StringUtils.isNotEmpty(req.getProcessName())) {
            query.processInstanceNameLikeIgnoreCase(req.getProcessName());
        }
        if(StringUtils.isNotEmpty(req.getProposer())) {
            query.startedBy(req.getProposer());
        }

        List<ProcessInstance> instanceList = query.listPage(req.getFirstResult(), req.getSize());
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (ProcessInstance pi : instanceList) {
            Map<String, Object> result = new HashMap<>();
            result.put("processInstanceId", pi.getProcessInstanceId());
            result.put("processInstanceName", pi.getName());
            result.put("processKey", pi.getProcessDefinitionKey());
            result.put("version", pi.getProcessDefinitionVersion());
            result.put("proposer", pi.getStartUserId());
            result.put("processStatus", pi.isSuspended() ? "?????????": "?????????");
            result.put("businessKey", pi.getBusinessKey());
            result.put("startTime", DateUtils.format(pi.getStartTime()) );

            // ?????????????????????????????????
            List<Task> taskList = taskService.createTaskQuery()
                    .processInstanceId(pi.getProcessInstanceId()).list();
            String currTaskInfo = ""; // ????????????
            for (Task task : taskList) {
                currTaskInfo += "????????????" + task.getName() + "??????????????????" + task.getAssignee() + "???<br>";
            }
            result.put("currTaskInfo", currTaskInfo);

            records.add(result);
        }

        Collections.sort(records, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                String date1 = (String)m1.get("startTime");
                String date2 = (String)m2.get("startTime");
                return date2.compareTo(date1);
            }
        });

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return Result.ok(result);
    }

    @Override
    public Result getProcInstFinish(ProcInstREQ req) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
                .finished() // ????????????
                .orderByProcessInstanceEndTime().desc();

        if(StringUtils.isNotEmpty(req.getProcessName())) {
            query.processInstanceNameLikeIgnoreCase(req.getProcessName());
        }
        if(StringUtils.isNotEmpty(req.getProposer())) {
            query.startedBy(req.getProposer());
        }

        List<HistoricProcessInstance> instanceList = query.listPage(req.getFirstResult(), req.getSize());
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (HistoricProcessInstance pi : instanceList) {
            Map<String, Object> result = new HashMap<>();
            result.put("processInstanceId", pi.getId());
            result.put("processInstanceName", pi.getName());
            result.put("processKey", pi.getProcessDefinitionKey());
            result.put("version", pi.getProcessDefinitionVersion());
            result.put("proposer", pi.getStartUserId());
            result.put("businessKey", pi.getBusinessKey());
            result.put("startTime", DateUtils.format(pi.getStartTime()) );
            result.put("endTime", DateUtils.format(pi.getEndTime()) );
            // ??????
            result.put("deleteReason", pi.getDeleteReason());

            // ????????????
            BusinessStatus businessStatus = businessStatusService.getById(pi.getBusinessKey());
            if(businessStatus != null) {
                result.put("status", BusinessStatusEnum.getEumByCode(businessStatus.getStatus()).getDesc() );
            }

            records.add(result);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return Result.ok(result);
    }

    @Override
    public Result deleteProcInstAndHistory(String procInstId) {
        // 1. ????????????????????????
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(procInstId).singleResult();

        // 2. ????????????????????????
        historyService.deleteHistoricProcessInstance(procInstId);
        historyService.deleteHistoricTaskInstance(procInstId);

        // 3. ????????????????????????, ?????????????????????id????????????????????????""????????????null,?????????????????????
        return businessStatusService.updateState(instance.getBusinessKey(), BusinessStatusEnum.DELETE, "");
    }


}
