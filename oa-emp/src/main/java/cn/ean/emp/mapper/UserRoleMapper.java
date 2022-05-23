package cn.ean.emp.mapper;

import cn.ean.emp.model.po.UserRolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ean
 * @FileName UserRoleMapper
 * @Date 2022/5/23 19:44
 **/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRolePO> {

    /**
     * 更新操作员角色
     * @param userId
     * @param roleIds
     * @return
     */
    Integer updateUserRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);


}
