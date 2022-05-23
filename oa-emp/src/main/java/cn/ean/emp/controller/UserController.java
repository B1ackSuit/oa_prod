package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.RolePO;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.service.IRoleService;
import cn.ean.emp.service.IUserService;
import io.swagger.annotations.ApiOperation;
import cn.ean.emp.aspect.Log;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ean
 * @FileName UserController
 * @Date 2022/5/23 21:53
 **/
@RestController
@RequestMapping("/system/user")
public class UserController {

    private IUserService userService;

    private IRoleService roleService;

    public UserController() {
    }

    @Autowired
    private UserController(IUserService adminService,
                            IRoleService roleService) {
        this.userService = adminService;
        this.roleService = roleService;
    }

    @Log(title = "获取所有用户", businessType = BusinessTypeEnum.LIST)
    @ApiOperation(value = "获取所有用户")
    @GetMapping("/")
    public List<UserPO> getAllUsers(String keyWords){
        return userService.getAllUsers(keyWords);
    }

    @Log(title = "更新用户")
    @ApiOperation(value = "更新用户")
    @PutMapping("/")
    public ResponseBO updateUser(@RequestBody UserPO userPO) {
        if (userService.updateById(userPO)) {
            return ResponseBO.success("用户更新成功");
        }
        return ResponseBO.error("用户更新失败");
    }

    @Log(title = "删除用户")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseBO deleteUser(@PathVariable Integer id) {
        if (userService.removeById(id)) {
            return ResponseBO.success("用户删除成功");
        }
        return ResponseBO.error("用户删除失败");

    }

    @Log(title = "获取所有角色")
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/roles")
    public List<RolePO> getAllRoles() {
        return roleService.list();
    }

    @Log(title = "更新用户角色")
    @ApiOperation(value = "更新用户角色")
    @PutMapping("/role")
    public ResponseBO updateUserRole(Integer userId, Integer[] rids) {
        return userService.updateUserRole(userId, rids);
    }


}
