package cn.ean.emp.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * * @EqualsAndHashCode(callSuper = true)，就是用自己的属性和从父类继承的属性来生成hashcode；
 * * @EqualsAndHashCode(callSuper = false)，就是只用自己的属性来生成hashcode；
 * @author ean
 * @FileName UserPO
 * @Date 2022/5/19 23:46
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oa_user")
@ApiModel(value = "UserPO对象", description = "UserPO对象")
public class UserPO{

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "工号")
    @TableField(value = "workId")
    private Integer workId;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
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
    private Boolean enabled;

    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<RolePO> roles;
}
