package cn.ean.emp.model.po;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ean
 * @FileName DepartmentPO
 * @Date 2022/5/22 21:33
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oa_department")
@ApiModel(value = "DepartmentPO对象", description = "DepartmentPO对象")
public class DepartmentPO {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称")
    @NonNull
    private String name;

    @ApiModelProperty(value = "父id")
    @TableField("parentId")
    private Integer parentId;

    @ApiModelProperty(value = "路径")
    @TableField("depPath")
    private String depPath;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "是否上级")
    @TableField("isParent")
    private Boolean isParent;

    @ApiModelProperty(value = "子部门列表")
    @TableField(exist = false)
    private List<DepartmentPO> children;

    @ApiModelProperty(value = "返回结果，存储过程使用")
    @TableField(exist = false)
    private Integer result;


}
