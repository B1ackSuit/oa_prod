package cn.ean.emp.mapper;

import cn.ean.emp.model.po.DepartmentPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ean
 * @FileName DepartmentMapper
 * @Date 2022/5/23 20:07
 **/
@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentPO> {

    /**
     * 获取所有部门
     * @param parentId
     * @return
     */
    List<DepartmentPO> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param dep
     */
    void addDep(DepartmentPO dep);


    /**
     * 删除部门
     * @param dep
     * @return
     */
    void deleteDep(DepartmentPO dep);

}
