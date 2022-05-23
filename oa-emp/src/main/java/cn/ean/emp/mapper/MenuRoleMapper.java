package cn.ean.emp.mapper;

import cn.ean.emp.model.po.MenuRolePO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ean
 * @FileName MenuRoleMapper
 * @Date 2022/5/23 20:59
 **/
@Mapper
public interface MenuRoleMapper extends BaseMapper<MenuRolePO> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

}
