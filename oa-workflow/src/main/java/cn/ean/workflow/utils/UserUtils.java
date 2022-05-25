package cn.ean.workflow.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author ean
 * @FileName UserUtils
 * @Date 2022/5/25 12:12
 **/
public class UserUtils {

    /**
     * 获取登录用户名
     * @return
     */
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        return authentication.getName();
    }


}
