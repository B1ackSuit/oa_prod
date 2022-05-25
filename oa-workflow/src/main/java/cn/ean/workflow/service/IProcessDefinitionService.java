package cn.ean.workflow.service;


import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.ProcDefiREQ;

public interface IProcessDefinitionService {

    /**
     * 条件分页查询流程定义列表数据
     * @param req
     * @return
     */
    Result getProcDefiList(ProcDefiREQ req);

    /**
     * 更新流程状态：激活或者挂起
     * @param ProcDefiId
     * @return
     */
    Result updateProcDefState(String ProcDefiId);

    /**
     * 删除流程定义
     * @param deploymentId 部署id
     * @param key 流程定义key
     * @return
     */
    Result deleteDeployment(String deploymentId, String key);




}
