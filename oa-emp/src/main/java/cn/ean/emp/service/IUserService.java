package cn.ean.emp.service;

import cn.ean.emp.model.bo.ResponseBO;
import cn.ean.emp.model.dto.RoleDTO;
import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.RolePO;
import cn.ean.emp.model.po.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ean
 * @FileName IUserService
 * @Date 2022/5/23 22:29
 **/
public interface IUserService extends IService<UserPO> {

    /**
     * 登录之后返回token
     * @param userName
     * @param password
     * @param code
     * @param request
     * @return
     */
    ResponseBO login(String userName, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    UserPO getUserByUserName(String userName);

    /**
     * 根据用户id查询角色列表
     * @param userId
     * @return
     */
    List<RolePO> getRoles(Integer userId);

    /**
     * 获取所有操作员
     * @param keyWords
     * @return
     */
    List<UserPO> getAllUsers(String keyWords);

    /**
     * 更新操作员角色
     * @param userId
     * @param rids
     * @return
     */
    ResponseBO updateUserRole(Integer userId, Integer[] rids);

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param userId
     * @return
     */
    ResponseBO updateUserPassword(String oldPass, String pass, Integer userId);

}
