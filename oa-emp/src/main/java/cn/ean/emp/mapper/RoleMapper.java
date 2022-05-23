package cn.ean.emp.mapper;

import cn.ean.emp.model.po.RolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ean
 * @FileName RoleMapper
 * @Date 2022/5/23 21:08
 **/
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<RolePO> getRoles(Integer userId);
}
