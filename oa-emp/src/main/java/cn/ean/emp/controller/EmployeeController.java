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

    @ApiOperation(value = "??????????????????(??????)")
    @GetMapping("/")
    public PageBO getEmployeeByPage(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    EmployeePO employeePO,
                                    LocalDate[] beginDateScope) {
        return employeeService.getEmployeeByPage(currentPage, size, employeePO, beginDateScope);
    }


    @ApiOperation(value = "????????????????????????")
    @GetMapping("/politicsStatus")
    public List<PoliticsStatusPO> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation(value = "??????????????????")
    @GetMapping("/jobLevels")
    public List<JobLevelPO> getAlljobLevels(){
        return jobLevelService.list();
    }

    @ApiOperation(value = "??????????????????")
    @GetMapping("/nations")
    public List<NationPO> getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "??????????????????")
    @GetMapping("/positions")
    public List<PositionPO> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation(value = "??????????????????")
    @GetMapping("/deps")
    public List<DepartmentPO> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "????????????")
    @GetMapping("/maxWorkId")
    public ResponseBO maxWorkId(){
        return employeeService.maxWorkId();
    }


    @Log(title = "????????????", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "????????????")
    @PostMapping("/")
    public ResponseBO addEmp(@RequestBody EmployeePO employee){
        return employeeService.addEmp(employee);
    }


    @Log(title = "????????????", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "????????????")
    @PutMapping("/")
    public ResponseBO updateEmp(@RequestBody EmployeePO employee){
        if (employeeService.updateById(employee)){
            return ResponseBO.success("???????????????");
        }
        return ResponseBO.error("???????????????");
    }

    @Log(title = "????????????", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "????????????")
    @DeleteMapping("/{id}")
    public ResponseBO deleteEmp(@PathVariable Integer id){
        if (employeeService.removeById(id)){
            return ResponseBO.success("???????????????");
        }
        return ResponseBO.error("???????????????");
    }

    @Log(title = "??????????????????", businessType = BusinessTypeEnum.EXPORT)
    @ApiOperation(value = "??????????????????")
    @GetMapping(value = "/export",produces = "application/octet-stream")
    public void exportEmployeePO(HttpServletResponse response) {

        List<EmployeePO> list = employeeService.getEmployee(null);
        /*
        HSSF???xls(03)??????
        XSSF???xlsx(07)??????
         */
        ExportParams params = new ExportParams("?????????","?????????", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, EmployeePO.class, list);
        ServletOutputStream out = null;

        try {
            //?????????
            response.setHeader("content-type","application/octet-stream");
            //??????????????????
            response.setHeader("content-disposition","attachment;filename=" +
                    URLEncoder.encode("?????????.xls","UTF-8"));
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

    @Log(title = "??????????????????", businessType = BusinessTypeEnum.IMPORT)
    @ApiOperation(value = "??????????????????")
    @PostMapping("/import")
    public ResponseBO importEmployeePO(MultipartFile file){
        ImportParams params = new ImportParams();
        //???????????????
        params.setTitleRows(1);
        List<NationPO> nationList = nationService.list();
        List<PoliticsStatusPO> politicsStatusList = politicsStatusService.list();
        List<DepartmentPO> departmentList = departmentService.list();
        List<JobLevelPO> jobLevelList = jobLevelService.list();
        List<PositionPO> positionList = positionService.list();
        try {
            List<EmployeePO> list = ExcelImportUtil.importExcel(file.getInputStream(), EmployeePO.class, params);
            list.forEach(employee -> {
                // ??????????????????????????????????????????

                //??????id
                employee.setNationId(nationList.get(nationList.indexOf(new NationPO(employee.getNationPO().getName()))).getId());
                //????????????id
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatusPO(employee.getPoliticsStatusPO().getName()))).getId());
                //??????id
                DepartmentPO departmentPO = new DepartmentPO(employee.getDepartmentPO().getName());
                System.out.println("EController insertEMP: new departmentPO.getName:" + departmentPO.getName());
                System.out.println("EController insertEMP: emp.getDepartmentPO.getName:" + employee.getDepartmentPO().getName());
                int i = departmentList.indexOf(employee.getDepartmentPO());
                for (DepartmentPO department : departmentList) {
                    if (department.getName().equals(departmentPO.getName())){
                        employee.setDepartmentId(department.getId());
                        System.out.println("EController insertEMP: foreach success");
                    }
                }
                System.out.println("EController insertEMP: departmentList.indexOf(departmentPO), Bound:" + i);
              //  employee.setDepartmentId(departmentList.get(departmentList.indexOf(new DepartmentPO(employee.getDepartmentPO().getName()))).getId());
                //??????id
                employee.setJobLevelId(jobLevelList.get(jobLevelList.indexOf(new JobLevelPO(employee.getJobLevelPO().getName()))).getId());
                //??????id
                employee.setPosId(positionList.get(positionList.indexOf(new PositionPO(employee.getPositionPO().getName()))).getId());
            });
            if (employeeService.saveBatch(list)){
                return ResponseBO.success("????????????!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBO.error("???????????????");
    }


}
