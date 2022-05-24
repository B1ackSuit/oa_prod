package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.DepartmentPO;
import cn.ean.emp.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ean
 * @FileName DepartmentController
 * @Date 2022/5/24 10:33
 **/
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    private IDepartmentService departmentService;

    @Autowired
    private DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public DepartmentController() {
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public List<DepartmentPO> getAllDepartments() {

        return departmentService.getAllDepartments();

    }

    @Log(title = "添加部门", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public ResponseBO addDep(@RequestBody DepartmentPO dep) {
        return departmentService.addDep(dep);
    }

    @Log(title = "删除部门", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public ResponseBO deleteDep(@PathVariable Integer id) {
        return departmentService.deleteDep(id);
    }
}
