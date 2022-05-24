package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.SalaryPO;
import cn.ean.emp.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ean
 * @FileName SalaryController
 * @Date 2022/5/24 14:23
 **/
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    private ISalaryService salaryService;

    @Autowired
    public SalaryController(ISalaryService salaryService) {
        this.salaryService = salaryService;
    }

    public SalaryController() {
    }

    @ApiOperation(value = "获取所有工资帐套")
    @GetMapping("/")
    public List<SalaryPO> getAllSalaries() {
        return salaryService.list();
    }

    @Log(title = "添加工资帐套", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加工资帐套")
    @PostMapping("/")
    public ResponseBO addSalary(@RequestBody SalaryPO salary) {
        salary.setCreateDate(LocalDateTime.now());

        if (salaryService.save(salary)) {
            return ResponseBO.success("添加工资帐套成功");
        }

        return ResponseBO.error("添加工资帐套失败");
    }

    @Log(title = "删除工资帐套", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除工资帐套")
    @DeleteMapping("/{id}")
    public ResponseBO deleteSalary(@PathVariable Integer id) {

        if (salaryService.removeById(id)) {
            return ResponseBO.success("删除工资帐套成功");
        }

        return ResponseBO.error("删除工资帐套失败");
    }

    @Log(title = "更新工资帐套", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新工资帐套")
    @PutMapping("/")
    public ResponseBO updateSalary(@RequestBody SalaryPO salary) {

        if (salaryService.updateById(salary)) {
            return ResponseBO.success("更新工资帐套成功");
        }

        return ResponseBO.error("更新工资帐套失败");
    }
}
