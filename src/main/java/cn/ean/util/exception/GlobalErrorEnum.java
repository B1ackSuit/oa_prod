package cn.ean.util.exception;

/**
 * 后续重构：枚举Error和Exception分开，细化相关错误和异常
 *
 * @author ean
 * @FileName GlobalError
 * @Date 2022/5/18 02:46
 **/
public enum GlobalErrorEnum {

    /**
     * 操作失败
     */
    OPERATION_FAILED(0, "操作失败"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION(-1, "系统异常"),

    /**
     * 参数为空异常
     */
    EMPTY_PARAMETER_EXCEPTION(2, "参数为空异常"),

    /**
     * 参数异常
     */
    PARAMETER_EXCEPTION(14, "参数异常"),

    /**
     * 参数绑定异常
     */
    PARAMETER_BINDING_EXCEPTION(3, "参数绑定异常"),

    /**
     * 文件上传异常
     */
    FILE_UPLOAD_EXCEPTION(4, "文件上传异常"),

    /**
     * 请求方式不支持/错误
     */
    REQUEST_METHOD_EXCEPTION(5, "请求方式不支持"),

    /**
     * 请检查url是否正确/请求路径异常
     */
    REQUEST_PATH_EXCEPTION(6, "请检查url是否正确"),

    /**
     * 权限异常/权限不足
     */
    PERMISSION_EXCEPTION(7, "权限不足"),

    /**
     * 未登录异常/尚未登录
     */
    NO_LOGIN_EXCEPTION(9, "尚未登陆"),

    /**
     * 接口异常
     */
    API_EXCEPTION(10, "接口异常"),

    /**
     * 数据解析异常
     */
    DATA_PARSING_EXCEPTION(11, "数据解析异常"),

    /**
     * HTTP接口响应异常
     */
    HTTP_INTERFACE_RESPONSE_EXCEPTION(12, "Http接口响应异常"),

    /**
     * 请求凭证有误
     */
    REQUEST_VOUCHER_ERROR(13, "请求凭证有误");

    private int code;
    private String errMsg;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    GlobalErrorEnum(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
