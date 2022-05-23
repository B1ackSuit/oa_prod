package cn.ean.emp.aspect;

import java.lang.annotation.*;

/**
 * 参考开源代码ruoyi的操作员日志模块，后续重构中
 *
 * @author ean
 * @FileName Log
 * @Date 2022/5/24 00:04
 **/
@Target({ ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 接口名
     */
    String title() default "";

    /**
     * 业务类型
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default false;

}
