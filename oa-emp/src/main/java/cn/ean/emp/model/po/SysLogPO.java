package cn.ean.emp.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author ean
 * @FileName SysLogPO
 * @Date 2022/5/23 17:50
 **/
@Data
@Accessors(chain = true)
@TableName("oa_sys_log")
@ApiModel(value="SysLogPO对象", description="SysLogPO对象")
public class SysLogPO {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 日志类型 0:info，1:error
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 功能
     */
    @ApiModelProperty(value = "功能")
    private String descr;

    /**
     * 主机
     */
    @ApiModelProperty(value = "主机")
    private String host;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    @TableField("ipAddress")
    private String ipAddress;

    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url")
    private String url;

    /**
     * 方法
     */
    @ApiModelProperty(value = "方法")
    private String method;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人id")
    @TableField("operUser")
    private String operUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @TableField("createTime")
    private LocalDateTime createTime;


}