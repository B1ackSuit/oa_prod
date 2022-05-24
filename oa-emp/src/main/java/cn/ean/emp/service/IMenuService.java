package cn.ean.emp.service;

import cn.ean.emp.model.po.MenuPO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ean
 * @FileName IMenuService
 * @Date 2022/5/24 09:43
 **/
public interface IMenuService extends IService<MenuPO> {

    /**
     * 根据用户id获取菜单列表
     * @return
     */
    List<MenuPO> getMenusByUserId();

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<MenuPO> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<MenuPO> getAllMenus();
}
