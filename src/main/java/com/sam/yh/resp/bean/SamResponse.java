package com.sam.yh.resp.bean;

import java.io.Serializable;

public class SamResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String result;
    private String resCode;
    private Object data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
