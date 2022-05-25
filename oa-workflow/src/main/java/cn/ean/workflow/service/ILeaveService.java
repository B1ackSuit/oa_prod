package cn.ean.workflow.service;


import cn.ean.workflow.model.bo.Result;
import cn.ean.workflow.model.dto.LeaveREQ;
import cn.ean.workflow.model.po.Leave;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ILeaveService extends IService<Leave> {

    Result add(Leave leave);

    /**
     * 条件分页查询请假列表
     * @param req
     * @return
     */
    Result listPage(LeaveREQ req);

    Result update(Leave leave);
}
