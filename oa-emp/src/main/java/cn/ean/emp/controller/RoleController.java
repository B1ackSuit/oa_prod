package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.MenuPO;
import cn.ean.emp.model.po.MenuRolePO;
import cn.ean.emp.model.po.RolePO;
import cn.ean.emp.service.IMenuRoleService;
import cn.ean.emp.service.IMenuService;
import cn.ean.emp.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ean
 * @FileName RoleController
 * @Date 2022/5/24 14:13
 **/
@RestController
@RequestMapping("/system/basic/permission")
public class RoleController {


    private static final String START_WITH_PERMISSION = "ROLE_";

    private IRoleService roleService;
    private IMenuService menuService;
    private IMenuRoleService menuRoleService;

    @Autowired
    public RoleController(IRoleService roleService,
                                 IMenuService menuService,
                                 IMenuRoleService menuRoleService) {
        this.roleService = roleService;
        this.menuService = menuService;
        this.menuRoleService = menuRoleService;
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/")
    public List<RolePO> getAllRoles() {
        return roleService.list();
    }

    @Log(title = "添加角色信息", businessType = BusinessTypeEnum.INSERT)
    @ApiOperation(value = "添加角色信息")
    @PostMapping("/role")
    public ResponseBO addRole(@RequestBody RolePO role) {

        if (!role.getAuthority().startsWith(START_WITH_PERMISSION)) {
            role.setAuthority(START_WITH_PERMISSION + role.getAuthority());
        }

        if (roleService.save(role)) {
            return ResponseBO.success("添加角色信息成功");
        }
        return ResponseBO.error("添加角色信息失败");
    }


//    @ApiOperation(value = "更新角色信息")
//    @PutMapping
//    public RespBo updateRole(@RequestBody Role role) {
//
//        if (!role.getName().startsWith(START_WITH_PERMISSION)) {
//            role.setName(START_WITH_PERMISSION + role.getName());
//        }
//
//        if (roleService.updateById(role)) {
//            return RespBo.success("更新角色信息成功");
//        }
//
//        return RespBo.error("更新角色信息失败");
//    }

    @Log(title = "删除角色信息", businessType = BusinessTypeEnum.DELETE)
    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/role/{rid}")
    public ResponseBO deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return ResponseBO.success("删除角色信息成功");
        }

        return ResponseBO.error("删除角色信息失败");
    }

    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<MenuPO> getAllMenus() {
        return menuService.getAllMenus();
    }

    @Log(title = "根据角色id查询菜单id", businessType = BusinessTypeEnum.LIST)
    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid) {
        return menuRoleService.list(
                        // 将取到的List<MenuRole>通过stream流转成mid
                        new QueryWrapper<MenuRolePO>().eq("rid", rid))
                .stream().map(MenuRolePO::getMid).collect(Collectors.toList()
                );
    }

    /**
     *
     * @param rid 角色id
     * @param mids 角色菜单数组
     * @return
     */
    @Log(title = "更新角色菜单", businessType = BusinessTypeEnum.UPDATE)
    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public ResponseBO updateMenuRole(Integer rid, Integer[] mids) {
        return menuRoleService.updateMenuRole(rid, mids);
    }

}
