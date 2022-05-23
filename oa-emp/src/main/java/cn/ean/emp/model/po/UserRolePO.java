package cn.ean.emp.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ean
 * @FileName UserRole
 * @Date 2022/5/22 20:55
 **/
@Data
@Accessors(chain = true)
@TableName("oa_user_role")
@ApiModel(value = "UserRolePO对象", description = "UserRolePO对象")
public class UserRolePO {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @TableField("userId")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @TableField("roleId")
    @ApiModelProperty(value = "角色id")
    private Integer roleId;


}
