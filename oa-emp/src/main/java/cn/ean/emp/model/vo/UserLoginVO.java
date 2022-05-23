package cn.ean.emp.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ean
 * @FileName UserLoginVO
 * @Date 2022/5/23 18:01
 **/
@Data
@Accessors(chain = false)
@ApiModel(value = "UserLoginVO对象", description = "UserLoginVO对象")
public class UserLoginVO {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    private String code;

}
