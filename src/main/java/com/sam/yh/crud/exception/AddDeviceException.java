package com.sam.yh.crud.exception;

public class AddDeviceException extends CrudException {

    public AddDeviceException() {
        super();
    }

    public AddDeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AddDeviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddDeviceException(String message) {
        super(message);
    }

    public AddDeviceException(Throwable cause) {
        super(cause);
    }

}
