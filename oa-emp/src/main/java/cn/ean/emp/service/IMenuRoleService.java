package cn.ean.emp.service;

import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.MenuRolePO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ean
 * @FileName IMenuRoleService
 * @Date 2022/5/24 10:58
 **/
public interface IMenuRoleService extends IService<MenuRolePO> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    ResponseBO updateMenuRole(Integer rid, Integer[] mids);
}
