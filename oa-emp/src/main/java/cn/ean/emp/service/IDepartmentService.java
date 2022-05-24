package cn.ean.emp.service;

import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.DepartmentPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ean
 * @FileName IDepartmentService
 * @Date 2022/5/24 10:33
 **/
public interface IDepartmentService extends IService<DepartmentPO> {

    /**
     * 获取所有部门
     * @return
     */
    List<DepartmentPO> getAllDepartments();

    /**
     * 添加部门
     * @param dep
     * @return
     */
    ResponseBO addDep(DepartmentPO dep);

    /**
     * 删除部门
     * @param id
     * @return
     */
    ResponseBO deleteDep(Integer id);
}
