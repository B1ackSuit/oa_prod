package cn.ean.workflow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.ean.workflow.enums.BusinessStatusEnum;
import cn.ean.workflow.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author ean
 * @FileName Loan
 * @Date 2022/5/25 10:15
 **/
@Data
@ApiModel("借款申请实体")
@TableName("loan")
public class Loan implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("申请人ID")
    private String userId;

    @ApiModelProperty("申请人昵称")
    private String nickName;

    @ApiModelProperty("借款金额")
    private Double money;

    @ApiModelProperty("借款用途")
    private String purpose;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("借款日期")
    private Date loanDate;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;


    @TableField(exist = false)
    @ApiModelProperty("流程实例id")
    private String processInstanceId;

    @TableField(exist = false)
    @ApiModelProperty("流程状态")
    private Integer status;

    public String getStatusStr() {
        if(this.status == null) {
            return "";
        }
        return BusinessStatusEnum.getEumByCode(this.status).getDesc();
    }

    public String getLoanDateStr() {
        if(loanDate == null) {
            return "";
        }
        return DateUtils.format(loanDate, DateUtils.DATE_FORMAT);
    }

    public String getCreateDateStr() {
        if(createDate == null) {
            return "";
        }
        return DateUtils.format(createDate);
    }

    public String getUpdateDateStr() {
        if(updateDate == null) {
            return "";
        }
        return DateUtils.format(updateDate);
    }

}
