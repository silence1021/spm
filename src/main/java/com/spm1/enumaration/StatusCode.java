package com.spm1.enumaration;

import lombok.Getter;


@Getter
public enum StatusCode {
    SUCCESS("success",200),ERROR("error",500);
    private String msg;
    private Integer code;

    StatusCode(String msg,Integer code){
        this.msg = msg;
        this.code = code;
    }
    StatusCode(Integer code){
        this.code = code;
    }
    StatusCode(String msg){
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
