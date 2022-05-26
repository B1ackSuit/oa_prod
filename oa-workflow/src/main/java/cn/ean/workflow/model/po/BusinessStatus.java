package cn.ean.workflow.model.po;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ean
 * @FileName BusinessStatus
 * @Date 2022/5/25 10:19
 **/
@Data
@ApiModel("业务状态关系实体")
@TableName("business_status")
public class BusinessStatus implements Serializable {

    @TableId
    @ApiModelProperty("业务ID")
    private String businessKey;

    @ApiModelProperty("流程实例ID")
    private String processInstanceId;

    @ApiModelProperty("流程状态：0已撤回, 1待提交，2处理中,3已完成, 4已作废，5已删除")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;

}
