package cn.ean.workflow.model.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;

/**
 * @author ean
 * @FileName BaseRequest
 * @Date 2022/5/25 10:26
 **/
@Data
@ApiModel("分页请求基础类")
public class BaseRequest<T> implements Serializable {

    @ApiModelProperty("页码")
    private int current;

    @ApiModelProperty("每页显示多少条")
    private int size;

    /**
     * 针对 mybatis-plus分页对象
     * @return
     */
    public Page<T> getPage() {
        return new Page<T>();
    }

    /**
     * activiti分页
     * @return
     */
    public Integer getFirstResult() {
        return (current - 1) * size;
    }

}

