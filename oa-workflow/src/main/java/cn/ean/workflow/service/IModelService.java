package cn.ean.workflow.service;



import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.ModelAddREQ;
import cn.ean.workflow.model.dto.ModelREQ;

import javax.servlet.http.HttpServletResponse;

public interface IModelService {

    /**
     * 新增模型基本信息（创建空的模型）
     * @param req
     * @return
     * @throws Exception
     */
    Result add(ModelAddREQ req) throws Exception;

    /**
     * 条件分页查询流程定义模型
     * @param req
     * @return
     */
    Result getModelList(ModelREQ req);

    /**
     * 通过模型数据部署流程定义
     * @param modelId
     * @return
     * @throws Exception
     */
    Result deploy(String modelId) throws Exception;

    /**
     * 将模型以zip的方式导出
     * @param modelId
     * @param response
     */
    void exportZip(String modelId, HttpServletResponse response);

}
