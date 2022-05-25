package cn.ean.workflow.service;



import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.ProcInstREQ;
import cn.ean.workflow.model.dto.StartREQ;

import javax.servlet.http.HttpServletResponse;

public interface IProcessInstanceService {

    /**
     * 提交申请启动实例
     * @param req
     * @return
     */
    Result startProcess(StartREQ req);

    /**
     * 撤回申请
     * @param businessKey
     * @param procInstId
     * @param message
     * @return
     */
    Result cancel(String businessKey, String procInstId, String message);

    /**
     * 通过流程实例id查询流程变量formName值
     * @param procInstId
     * @return
     */
    Result getFormNameByProcInstId(String procInstId);

    Result getHistoryInfoList(String procInstId);

    /**
     * 查询流程实例审批历史流程图
     * @param prodInstId
     * @param response
     */
    void getHistoryProcessImage(String prodInstId, HttpServletResponse response);

    /**
     * 查询正在运行的流程实例
     * @param req
     * @return
     */
    Result getProcInstRunning(ProcInstREQ req);

    /**
     * 查询已结束的流程实例
     * @param req
     * @return
     */
    Result getProcInstFinish(ProcInstREQ req);

    /**
     * 删除流程实例与历史记录
     * @param procInstId
     * @return
     */
    Result deleteProcInstAndHistory(String procInstId);
}
