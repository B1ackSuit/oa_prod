package cn.ean.emp.util;


import cn.ean.emp.model.dto.UserDTO;
import cn.ean.emp.model.po.UserPO;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户工具类
 *
 * @author ean
 * @FileName UserUtils
 * @Date 2022/5/18 21:01
 **/
public class UserUtils {

    /**
     * 获取当前登录的用户
     *
     * @return UserPO
     */
    public static UserPO getCurrentUser() {
        return (UserPO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
