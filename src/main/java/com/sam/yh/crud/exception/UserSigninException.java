package com.sam.yh.crud.exception;

public class UserSigninException extends CrudException {

    public UserSigninException() {
        super();
    }

    public UserSigninException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserSigninException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSigninException(String message) {
        super(message);
    }

    public UserSigninException(Throwable cause) {
        super(cause);
    }

}