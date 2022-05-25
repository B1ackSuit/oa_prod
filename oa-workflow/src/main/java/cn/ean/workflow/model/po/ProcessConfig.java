package cn.ean.workflow.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.ean.workflow.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ean
 * @FileName ProcessConfig
 * @Date 2022/5/25 10:09
 **/
@Data
@ApiModel("流程定义配置实体")
@TableName("process_config")
public class ProcessConfig implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("流程KEY")
    private String processKey;

    @ApiModelProperty("业务列表路由名")
    private String businessRoute;

    @ApiModelProperty("关联业务表单组件名")
    private String formName;

    @ApiModelProperty("创建时间")
    private Date createDate;

    @ApiModelProperty("更新时间")
    private Date updateDate;

    // createDateStr
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
