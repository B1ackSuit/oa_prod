package cn.ean.emp.mapper;

import cn.ean.emp.model.po.MenuPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ean
 * @FileName MenuMapper
 * @Date 2022/5/23 20:45
 **/
@Mapper
public interface MenuMapper extends BaseMapper<MenuPO> {
    /**
     * 根据id获取菜单列表
     * @param id
     * @return
     */
    List<MenuPO> getMenusByUserId(Integer id);


    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    List<MenuPO> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuPO> getAllMenus();
}
