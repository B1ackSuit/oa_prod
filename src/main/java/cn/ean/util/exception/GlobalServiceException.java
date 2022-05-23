package cn.ean.util.exception;

/**
 * 全局业务层异常处理类
 *
 * @author ean
 * @FileName GlobalServiceException
 * @Date 2022/5/18 14:49
 **/
public class GlobalServiceException extends RuntimeException{

    private String msg;

    private int code = 0;

    private GlobalErrorEnum error;

    public GlobalServiceException(GlobalErrorEnum error) {
        super(error.getErrMsg());
        this.error = error;
        this.code = error.getCode();
        this.msg = error.getErrMsg();
    }

    public GlobalServiceException(GlobalErrorEnum error, String msg) {
        super(msg);
        this.error = error;
        this.code = error.getCode();
        this.msg = error.getErrMsg() + " " + msg;
    }

    public GlobalServiceException() {
    }

    public GlobalServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalServiceException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public GlobalServiceException setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return this.code;
    }

    public GlobalServiceException setCode(int code) {
        this.code = code;
        return this;
    }

    public GlobalErrorEnum getError() {
        return this.error;
    }


}
