package cn.ean.emp.model.dto;

import cn.ean.emp.config.CustomAuthorityDeserializer;
import cn.ean.emp.model.po.RolePO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * * @EqualsAndHashCode(callSuper = true)，就是用自己的属性和从父类继承的属性来生成hashcode；
 * * @EqualsAndHashCode(callSuper = false)，就是只用自己的属性来生成hashcode；
 * * @Data后，callSuper默认为false
 *
 * @author ean
 * @FileName UserDTO
 * @Date 2022/5/20 03:21
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "UserDTO对象", description = "UserDTO对象")
public class UserDTO implements Serializable, UserDetails {

    /**
     * 序列化与反序列化的版本ID，序列化后如果需要反序列化，需要统一此UID
     */
    private static final long serializableUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * Data注解会自动生成Getter方法，但已经实现了UserDetails并重写了getUsername，
     * 这会导致Mybatis不知道用哪一个方法获取Username
     */
    @ApiModelProperty(value = "工号")
    @Getter(AccessLevel.NONE)
    private Integer workId;

    /**
     * Data注解会自动生成Getter方法，但已经实现了UserDetails并重写了getPassword，
     * 这会导致Mybatis不知道用哪一个方法获取password
     */
    @ApiModelProperty(value = "密码")
    @Getter(AccessLevel.NONE)
    private String password;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String userFace;

    /**
     * Data注解会自动生成Getter方法，但已经实现了UserDetails并重写了isEnable，
     * 这会导致Mybatis不知道用哪一个方法获取enable
     */
    @ApiModelProperty(value = "是否启用")
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

    @ApiModelProperty(value = "角色")
    private List<RolePO> rolePOs;


    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * 把对应的角色名转化为SimpleGrantedAuthority
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return rolePOs
                .stream()
                .map(rolePO -> new SimpleGrantedAuthority(rolePO.getAuthority()))
                .collect(Collectors.toList());

    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return String.valueOf(workId);
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
