package cn.ean.emp.controller;

import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.model.vo.UserLoginVO;
import cn.ean.emp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author ean
 * @FileName LoginController
 * @Date 2022/5/24 10:21
 **/
@Api(tags = "LoginController")
@RestController
public class LoginController {

    private IUserService userService;


    @Autowired
    public LoginController(IUserService userService) {
        this.userService = userService;
    }

    @Log(title = "登录")
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public ResponseBO login(@RequestBody UserLoginVO userLoginVO, HttpServletRequest request) {
        System.out.println("====LoginController login() debugger:" + userLoginVO.toString());

        return userService.login(userLoginVO.getUsername(),
                userLoginVO.getPassword(),
                userLoginVO.getCode(), request);
    }


    /**
     * 退出后，前端删除token，之后访问接口会被拦截
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ResponseBO logout() {
        return ResponseBO.success("注销成功!");
    }


    /**
     * 登录时在Spring Security里set了authenticationToken对象，所以可以直接通过Principal获取
     * @param principal
     * @return
     */
    @Log(title = "获取当前登录用户的信息")
    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/user/info")
    public UserPO getAdminInfo(Principal principal) {
        System.out.println("\n\n====LoginController getAdminInfo() debugger: " +
                "\nPrincipal: " + principal);

        if (null == principal) {
            return null;
        }

        String name = principal.getName();
        System.out.println("\n\n====LoginController getAdminInfo() debugger: " +
                "\nname: " + name);
        UserPO user = userService.getUserByUserName(name);
        // 前端不需要用户密码，设置为null作为保护
        user.setPassword(null);
        user.setRoles(userService.getRoles(user.getId()));
        System.out.println("\n\n====LoginController getAdminInfo() debugger: " +
                "\nLoginUser userFace: " + user.getUserFace());
        return user;

    }
}
