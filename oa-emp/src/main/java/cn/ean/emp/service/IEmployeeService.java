package cn.ean.emp.service;

import cn.ean.emp.model.bo.PageBO;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.EmployeePO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ean
 * @FileName IEmployeeService
 * @Date 2022/5/24 11:10
 **/
public interface IEmployeeService extends IService<EmployeePO> {

    /**
     * 获取所有员工(分页)
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    PageBO getEmployeeByPage(Integer currentPage, Integer size, EmployeePO employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return
     */
    ResponseBO maxWorkId();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    ResponseBO addEmp(EmployeePO employee);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<EmployeePO> getEmployee(Integer id);

    /**
     * 获取所有员工帐套
     * @param currentPage
     * @param size
     * @return
     */
    PageBO getEmployeeWithSalary(Integer currentPage, Integer size);
}
