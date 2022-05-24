package cn.ean.emp.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * @author ean
 * @FileName RoleUrlDecisionManager
 * @Date 2022/5/24 09:53
 **/
@Component
public class RoleUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute configAttribute : configAttributes) {
            //当前url所需角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否登录即可访问的角色，此角色在RoleFilter中设置
            if ("ROLE_LOGIN".equals(needRole)){
                //判断是否登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录！");
                }else {
                    return;
                }
            }
            //判断用户角色是否为url所需角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if(null == authorities) {
                System.out.println("RoleUrlDecision: authorities == null");
            }
            assert authorities != null;
            for (GrantedAuthority authority : authorities) {

                System.out.println("RoleUrlDecision: has authority: " + authority.getAuthority());
                System.out.println("RoleUrlDecision: needRole: " + needRole);

                if (authority.getAuthority().equals(needRole)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
