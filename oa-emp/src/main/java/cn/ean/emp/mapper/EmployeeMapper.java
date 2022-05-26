package cn.ean.emp.mapper;

import cn.ean.emp.model.po.EmployeePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ean
 * @FileName EmployeeMapper
 * @Date 2022/5/23 20:11
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeePO> {
    /**
     * 获取所有员工(分页)
     * @param page
     * @param employeePO
     * @param beginDateScope
     * @return
     */
    IPage<EmployeePO> getEmployeeByPage(Page<EmployeePO> page, @Param("employeePO") EmployeePO employeePO,
                                        @Param("beginDateScope") LocalDate[] beginDateScope);


    /**
     * 查询员工
     * @param id
     * @return
     */
    List<EmployeePO> getEmployee(Integer id);

    /**
     * 获取所有员工帐套
     * @param page
     * @return
     */
    IPage<EmployeePO> getEmployeeWithSalary(Page<EmployeePO> page);


    List<EmployeePO> getEmployeeLimit10();
}
