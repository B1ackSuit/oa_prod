package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.MenuMapper;
import cn.ean.emp.model.po.MenuPO;
import cn.ean.emp.service.IMenuService;
import cn.ean.emp.util.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ean
 * @FileName MenuServiceImpl
 * @Date 2022/5/24 09:45
 **/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuPO> implements IMenuService {

    private MenuMapper menuMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    private MenuServiceImpl(MenuMapper menuMapper, RedisTemplate redisTemplate) {
        this.menuMapper = menuMapper;
        this.redisTemplate = redisTemplate;
    }

    public MenuServiceImpl() {
    }

    /**
     * 根据用户id获取菜单列表
     *
     * @return
     */
    @Override
    public List<MenuPO> getMenusByUserId() {
        Integer adminId = UserUtils.getCurrentUser().getId();

        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        // 从Redis获取菜单数据
        List<MenuPO> menuList = (List<MenuPO>) valueOperations.get("menu_" + adminId);
        System.out.println("===MenuServiceImpl debugger: getMenusByAdminId(): menuListFromRedis:" + menuList);
        // 如果为空，去数据库获取
        if (CollectionUtils.isEmpty(menuList)) {
            menuList = menuMapper.getMenusByUserId(adminId);
            System.out.println("===MenuServiceImpl debugger: getMenusByAdminId(): menuListFromDB:" + menuList);
            // 将数据设置到Redis中
            valueOperations.set("menu_" + adminId, menuList);
        }

        return menuList;
    }

    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    @Override
    public List<MenuPO> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @Override
    public List<MenuPO> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
