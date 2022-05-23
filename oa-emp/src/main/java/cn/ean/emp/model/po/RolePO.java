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

/**
 * @author ean
 * @FileName RolePO
 * @Date 2022/5/20 15:53
 **/

@Data
@Accessors(chain = true)
@TableName("oa_role")
@ApiModel(value="RolePO对象", description="RolePO对象")
public class RolePO {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @ApiModelProperty(value = "角色")
    @TableField("authority")
    private String authority;

    @ApiModelProperty(value = "角色名称")
    @TableField("authorityName")
    private String authorityName;

}
