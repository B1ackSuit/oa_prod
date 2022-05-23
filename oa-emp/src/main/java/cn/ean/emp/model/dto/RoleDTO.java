package cn.ean.emp.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ean
 * @FileName RoleDTO
 * @Date 2022/5/21 00:20
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "RoleDTO对象", description = "RoleDTO对象")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色")
    private String authority;

    @ApiModelProperty(value = "角色名称")
    private String authorityName;

}
