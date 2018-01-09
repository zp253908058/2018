package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ResponseEntity
 * @since 2017/8/1
 */

public class ResponseEntity<T> {

    private int result;//状态码
    @SerializedName(value = "message", alternate = {"msg"})
    private String msg;//消息
    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(T data) {
        this.data = data;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
