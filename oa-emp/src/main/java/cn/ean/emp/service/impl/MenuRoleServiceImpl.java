package cn.ean.emp.service.impl;

import cn.ean.emp.mapper.MenuRoleMapper;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.MenuRolePO;
import cn.ean.emp.service.IMenuRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ean
 * @FileName MenuRoleServiceImpl
 * @Date 2022/5/24 10:58
 **/
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRolePO> implements IMenuRoleService {

    private MenuRoleMapper menuRoleMapper;

    @Autowired
    private MenuRoleServiceImpl(MenuRoleMapper menuRoleMapper) {
        this.menuRoleMapper = menuRoleMapper;
    }

    public MenuRoleServiceImpl() {
    }

    /**
     * 更新角色菜单
     *
     * @param rid
     * @param mids
     * @return
     */
    @Override
    @Transactional
    public ResponseBO updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRolePO>().eq("rid", rid));
        if (null == mids || mids.length == 0) {
            return ResponseBO.success("更新角色菜单成功");
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if (mids.length == result) {
            return ResponseBO.success("更新角色菜单成功");
        }
        return ResponseBO.error("更新角色菜单失败");
    }
}

