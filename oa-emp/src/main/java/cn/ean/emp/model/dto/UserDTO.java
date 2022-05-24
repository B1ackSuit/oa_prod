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
public class UserDTO  {


    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * Data注解会自动生成Getter方法，但已经实现了UserDetails并重写了getUsername，
     * 这会导致Mybatis不知道用哪一个方法获取Username
     */
    @ApiModelProperty(value = "工号")
    private Integer workId;

    /**
     * Data注解会自动生成Getter方法，但已经实现了UserDetails并重写了getPassword，
     * 这会导致Mybatis不知道用哪一个方法获取password
     */
    @ApiModelProperty(value = "密码")
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
    private Boolean enabled;

    @ApiModelProperty(value = "角色")
    private List<RolePO> rolePOs;



}
