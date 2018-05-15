package com.streamstech.droid.mshtb.data;

public class ServerResponse {
    private int errcode;
    private String message;
    private String rawresponse;
    private boolean success;
    private Object result;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrcode() {
        return this.errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRawresponse() {
        return this.rawresponse;
    }

    public void setRawresponse(String rawresponse) {
        this.rawresponse = rawresponse;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
