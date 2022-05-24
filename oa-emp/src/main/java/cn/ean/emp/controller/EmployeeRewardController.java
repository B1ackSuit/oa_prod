package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeeRewardPO;
import cn.ean.emp.service.IEmployeeRewardService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ean
 * @FileName EmployeeRewardController
 * @Date 2022/5/24 10:38
 **/

@RestController
@RequestMapping("/personnel/ec")
public class EmployeeRewardController {

    private IEmployeeRewardService employeeRewardService;

    @Autowired
    private EmployeeRewardController(IEmployeeRewardService employeeRewardService) {
        this.employeeRewardService = employeeRewardService;
    }

    public EmployeeRewardController() {
    }

    @Log(title = "获取所有员工奖惩数据（分页）", businessType = BusinessTypeEnum.LIST)
    @ApiOperation(value = "获取所有员工奖惩数据（分页）")
    @GetMapping("/")
    public PageBO getRewardsByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size){
        return employeeRewardService.getRewardsByPage(currentPage, size);
    }

    @Log(title = "添加员工奖惩数据", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加员工奖惩数据")
    @PostMapping("/")
    public ResponseBO addInfo(@RequestBody EmployeeRewardPO employeeReward){
        return employeeRewardService.updateInfo(employeeReward, "insert");
    }

    @Log(title = "更新员工奖惩数据", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新员工奖惩数据")
    @PutMapping("/")
    public ResponseBO updateEmp(@RequestBody EmployeeRewardPO employeeReward){
        return employeeRewardService.updateInfo(employeeReward, "update");
    }

    @Log(title = "删除员工奖惩数据", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除员工奖惩数据")
    @DeleteMapping("/{id}")
    public ResponseBO deleteEmp(@PathVariable Integer id){
        EmployeeRewardPO employeeRewardPO = employeeRewardService.getById(id);
        if (employeeRewardPO != null) {
            return employeeRewardService.deleteEmpById(employeeRewardPO);
        }
        else {
            return ResponseBO.error("删除失败！");
        }
//        if (employeeRewardService.deleteEmpById(id)){
//
//            return ResponseBO.success("删除成功！");
//        }
//
    }

}

