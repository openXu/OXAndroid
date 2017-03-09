package com.openxu.libdemo.evenbus;

/**
 * Created by openXu on 2017/3/7.
 */

public class MessageEvent {

    private int code;
    private String msg;

    public MessageEvent(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
