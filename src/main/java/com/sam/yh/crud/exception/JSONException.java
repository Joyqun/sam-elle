package com.sam.yh.crud.exception;

public class JSONException extends CrudException {

    public JSONException() {
        super();
    }

    public JSONException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public JSONException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONException(String message) {
        super(message);
    }

    public JSONException(Throwable cause) {
        super(cause);
    }

}