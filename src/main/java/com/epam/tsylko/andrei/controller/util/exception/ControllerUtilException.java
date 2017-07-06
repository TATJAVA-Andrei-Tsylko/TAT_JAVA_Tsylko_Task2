package com.epam.tsylko.andrei.controller.util.exception;


public class ControllerUtilException extends Exception {
    public ControllerUtilException() {
        super();
    }

    public ControllerUtilException(String message) {
        super(message);
    }

    public ControllerUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerUtilException(Throwable cause) {
        super(cause);
    }
}
