package cn.ean.emp.model.po;

import cn.ean.emp.config.CustomAuthorityDeserializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * * @RequiredArgsConstructor 配合@NonNull可以生成专门的
 *
 * @author ean
 * @FileName UserPO
 * @Date 2022/5/19 23:46
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_user")
@ApiModel(value = "UserPO对象", description = "UserPO对象")
public class UserPO implements Serializable, UserDetails {

    /**
     * 序列化与反序列化的版本ID，序列化后如果需要反序列化，需要统一此UID
     */
    private static final long serializableUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "工号")
    @TableField(value = "workId")
    @Getter(AccessLevel.NONE)
    private Integer workId;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    @Getter(AccessLevel.NONE)
    private String password;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "手机号")
    @TableField(value = "phone")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    @TableField(value = "userFace")
    private String userFace;

    @ApiModelProperty(value = "是否启用")
    @TableField(value = "enabled")
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<RolePO> roles;



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
        List<SimpleGrantedAuthority> collect = roles
                .stream()
                .map(rolePO -> new SimpleGrantedAuthority(rolePO.getAuthority()))
                .collect(Collectors.toList());

        return collect;

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
