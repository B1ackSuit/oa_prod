package cn.ean.emp.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ean
 * @FileName ResponseBO
 * @Date 2022/5/23 17:56
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBO {

    private long code;

    private String message;

    private Object obj;


    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static ResponseBO success(String message) {
        return new ResponseBO(200, message, null);
    }


    /**
     * 成功返回结果(带对象)
     * @param message
     * @param obj
     * @return
     */
    public static ResponseBO success(String message, Object obj) {
        return new ResponseBO(200, message, obj);
    }


    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static ResponseBO error(String message) {
        return new ResponseBO(500, message, null);
    }

    /**
     * 失败返回结果(带对象)
     * @param message
     * @param obj
     * @return
     */
    public static ResponseBO error(String message, Object obj) {
        return new ResponseBO(500, message, obj);
    }



}
