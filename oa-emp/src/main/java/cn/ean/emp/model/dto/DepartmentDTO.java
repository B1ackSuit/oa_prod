package cn.ean.emp.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.ean.emp.model.po.DepartmentPO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author ean
 * @FileName DepartmentDTO
 * @Date 2022/5/22 21:36
 **/
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "DepartmentDTO对象", description = "DepartmentDTO对象")
public class DepartmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    @NonNull
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "路径")
    private String depPath;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "是否上级")
    private Boolean isParent;

    @ApiModelProperty(value = "子部门列表")
    private List<DepartmentPO> children;

    @ApiModelProperty(value = "返回结果，存储过程使用")
    private Integer result;

}
