package cn.ean.emp.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * @author ean
 * @FileName EmployeeReward
 * @Date 2022/5/23 15:55
 **/
@Data
@Accessors(chain = true)
@TableName("t_employee_reward")
@ApiModel(value="reward对象", description="")
public class EmployeeRewardPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    private Integer eid;

    @ApiModelProperty(value = "员工姓名")
    private String ename;

    @ApiModelProperty(value = "员工账套id")
    private Integer sid;
}

