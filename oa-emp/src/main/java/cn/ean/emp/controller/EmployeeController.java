package cn.ean.emp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.*;
import cn.ean.emp.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * @author ean
 * @FileName EmployeeController
 * @Date 2022/5/24 11:09
 **/
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    private IEmployeeService employeeService;

    private IPoliticsStatusService politicsStatusService;

    private IJobLevelService jobLevelService;

    private INationService nationService;

    private IPositionService positionService;

    private IDepartmentService departmentService;

    @Autowired
    private EmployeeController(IEmployeeService employeeService,
                               IPoliticsStatusService politicsStatusService,
                               IJobLevelService jobLevelService,
                               INationService nationService,
                               IPositionService positionService,
                               IDepartmentService departmentService) {
        this.employeeService = employeeService;
        this.politicsStatusService = politicsStatusService;
        this.jobLevelService = jobLevelService;
        this.nationService = nationService;
        this.positionService = positionService;
        this.departmentService = departmentService;
    }

    public EmployeeController() {
    }

    @ApiOperation(value = "获取所有员工(分页)")
    @GetMapping("/")
    public PageBO getEmployeeByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    EmployeePO employeePO,
                                    LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(currentPage, size, employeePO, beginDateScope);
    }


    @ApiOperation(value = "获取所有政治面貌")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatusPO> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/jobLevels")
    public List<JobLevelPO> getAlljobLevels(){
        return jobLevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<NationPO> getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<PositionPO> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/deps")
    public List<DepartmentPO> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkId")
    public ResponseBO maxWorkId(){
        return employeeService.maxWorkId();
    }


    @Log(title = "添加员工", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public ResponseBO addEmp(@RequestBody EmployeePO employee){
        return employeeService.addEmp(employee);
    }


    @Log(title = "更新员工", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public ResponseBO updateEmp(@RequestBody EmployeePO employee){
        if (employeeService.updateById(employee)){
            return ResponseBO.success("更新成功！");
        }
        return ResponseBO.error("更新失败！");
    }

    @Log(title = "删除员工", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public ResponseBO deleteEmp(@PathVariable Integer id){
        if (employeeService.removeById(id)){
            return ResponseBO.success("删除成功！");
        }
        return ResponseBO.error("删除失败！");
    }

    @Log(title = "导出员工数据", businessType = BusinessTypeEnum.EXPORT)
    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportEmployeePO(HttpServletResponse response) {

        List<EmployeePO> list = employeeService.getEmployee(null);
        /*
        HSSF是xls(03)版本
        XSSF是xlsx(07)版本
         */
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, EmployeePO.class, list);
        ServletOutputStream out = null;

        try {
            //流形式
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename=" +
                    URLEncoder.encode("员工表.xls","UTF-8"));
            out = response.getOutputStream();
            workbook.write(out);
            System.out.println("=====debugger EmployeePOController exportEmployeePO:  workbook:" + workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Log(title = "导入员工数据", businessType = BusinessTypeEnum.IMPORT)
    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public ResponseBO importEmployeePO(MultipartFile file){
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<NationPO> nationList = nationService.list();
        List<PoliticsStatusPO> politicsStatusList = politicsStatusService.list();
        List<DepartmentPO> departmentList = departmentService.list();
        List<JobLevelPO> jobLevelList = jobLevelService.list();
        List<PositionPO> positionList = positionService.list();
        try {
            List<EmployeePO> list = ExcelImportUtil.importExcel(file.getInputStream(), EmployeePO.class, params);
            list.forEach(employee -> {
                // 此处可以后续完善逻辑外键关联

                //民族id
                employee.setNationId(nationList.get(nationList.indexOf(new NationPO(employee.getNationPO().getName()))).getId());
                //政治面貌id
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatusPO(employee.getPoliticsStatusPO().getName()))).getId());
                //部门id
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new DepartmentPO(employee.getDepartmentPO().getName()))).getId());
                //职称id
                employee.setJobLevelId(jobLevelList.get(jobLevelList.indexOf(new JobLevelPO(employee.getJobLevelPO().getName()))).getId());
                //职位id
                employee.setPosId(positionList.get(positionList.indexOf(new PositionPO(employee.getPositionPO().getName()))).getId());
            });
            if (employeeService.saveBatch(list)){
                return ResponseBO.success("导入成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBO.error("导入失败！");
    }


}
