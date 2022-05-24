package cn.ean.emp.controller;

import cn.ean.emp.aspect.BusinessTypeEnum;
import cn.ean.emp.aspect.Log;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ean
 * @FileName ChatController
 * @Date 2022/5/24 10:25
 **/
@RestController
@RequestMapping("/chat")
public class ChatController {
    private IUserService userService;

    @Autowired
    public ChatController(IUserService userService) {
        this.userService = userService;
    }

    @Log(title = "获取所有用户（聊天）", businessType = BusinessTypeEnum.LIST)
    @ApiOperation(value = "获取所有用户")
    @GetMapping("/user")
    public List<UserPO> getAllUsers(String keyWords) {
        return userService.getAllUsers(keyWords);
    }
}
