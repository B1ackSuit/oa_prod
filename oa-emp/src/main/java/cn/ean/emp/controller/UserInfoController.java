package cn.ean.emp.controller;

import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.UserPO;
import cn.ean.emp.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ean
 * @FileName UserInfoController
 * @Date 2022/5/24 09:15
 **/
@RestController
public class UserInfoController {


    private IUserService userService;

    @Autowired
    public UserInfoController(IUserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/admin/info")
    public ResponseBO updateAdmin(@RequestBody UserDTO user, Authentication authentication){
        UserPO userPO = new UserPO()
                .setId(user.getId())
                .setName(user.getName())
                .setPhone(user.getPhone());

        if (userService.updateById(userPO)){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            user,null,authentication.getAuthorities()));
            return ResponseBO.success("当前用户信息更新成功！");
        }
        return ResponseBO.error("当前用户信息更新失败！");
    }


    @ApiOperation(value = "更新用户密码")
    @PutMapping("/admin/pass")
    public ResponseBO updateAdminPassword(@RequestBody Map<String,Object> info){
        String oldPass = (String) info.get("oldPass");
        String pass = (String) info.get("pass");
        Integer userId = (Integer) info.get("adminId");
        return userService.updateUserPassword(oldPass,pass,userId);
    }

    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userface")
    public ResponseBO updateAdminUserFace(MultipartFile file, Integer id, Authentication authentication){
		/*String[] filePath = FastDFSUtils.upload(file);
		String url = FastDFSUtils.getTrackerUrl() + filePath[0] + "/" + filePath[1];*/
        return null;
    }

}
