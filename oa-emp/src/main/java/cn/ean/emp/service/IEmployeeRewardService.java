package cn.ean.emp.service;

import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeeRewardPO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ean
 * @FileName IEmployeeRewardService
 * @Date 2022/5/24 10:39
 **/
public interface IEmployeeRewardService extends IService<EmployeeRewardPO> {

    PageBO getRewardsByPage(Integer currentPage, Integer size);

    ResponseBO updateInfo(EmployeeRewardPO employeeReward, String type);

    ResponseBO updateEmployeeReward(EmployeeRewardPO employeeReward);

    ResponseBO deleteEmpById(EmployeeRewardPO employeeRewardPO);
}
