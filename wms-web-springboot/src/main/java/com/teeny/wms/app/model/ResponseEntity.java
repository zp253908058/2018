package com.teeny.wms.app.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ResponseEntity
 * @since 2017/10/16
 */
public class ResponseEntity<T> {

    private static final String MSG_SUCCESS = "请求成功.";

    private int result;
    private String message;
    private T data;

    public ResponseEntity() {
        this.result = 0;
        this.message = MSG_SUCCESS;
    }

    public ResponseEntity(T data) {
        this();
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ResponseEntity create(int code, String message) {
        ResponseEntity result = new ResponseEntity();
        result.setResult(code);
        result.setMessage(message);
        return result;
    }
}
