package cn.ean.emp.controller;

import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeePO;
import cn.ean.emp.model.po.SalaryPO;
import cn.ean.emp.service.IEmployeeService;
import cn.ean.emp.service.ISalaryService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ean
 * @FileName SalarySobCfgController
 * @Date 2022/5/24 14:25
 **/
@RestController
@RequestMapping("/salary/salemp")
public class SalaryEmpController {

    private ISalaryService salaryService;

    private IEmployeeService employeeService;

    @Autowired
    private SalaryEmpController(ISalaryService salaryService,
                                   IEmployeeService employeeService) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
    }

    public SalaryEmpController() {
    }

    @ApiOperation(value = "获取所有工资账套")
    @GetMapping("/salaries")
    public List<SalaryPO> getAllSalaries(){
        return salaryService.list();
    }

    @ApiOperation(value = "获取所有员工账套")
    @GetMapping("/")
    public PageBO getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(currentPage,size);
    }

    @ApiOperation(value = "更新员工账套")
    @PutMapping("/")
    public ResponseBO updateEmployeeSalary(Integer eid, Integer sid){
        if (employeeService.update(new UpdateWrapper<EmployeePO>().set("salaryId",sid)
                .eq("id",eid))){
            return ResponseBO.success("员工账套更新成功！");
        }
        return ResponseBO.error("员工账套更新失败！");
    }

}
