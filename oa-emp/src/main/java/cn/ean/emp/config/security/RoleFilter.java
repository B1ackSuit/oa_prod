package cn.ean.emp.config.security;

import cn.ean.emp.model.po.MenuPO;
import cn.ean.emp.model.po.RolePO;
import cn.ean.emp.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


/**
 * 权限控制
 * 根据请求url分析请求所需的角色
 * @author ean
 * @FileName RoleFilter
 * @Date 2022/5/24 09:42
 **/
@Component
public class RoleFilter implements FilterInvocationSecurityMetadataSource {


    private IMenuService menuService;

    @Autowired
    private RoleFilter(IMenuService menuService) {
        this.menuService = menuService;
    }

    public RoleFilter() {
    }

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println("=====debugger: RoleFilter getAttributes requestUrl:" + requestUrl + "\n");
        List<MenuPO> menus = menuService.getMenusWithRole();
        for (MenuPO menu : menus) {

            if (antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] str = menu.getRoles().stream().map(RolePO::getAuthority).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        //没匹配的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
