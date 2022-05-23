package cn.ean.emp.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ean
 * @FileName UserRoleDTO
 * @Date 2022/5/22 20:58
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "UserRoleDTO对象", description = "UserRoleDTO对象")
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

}
