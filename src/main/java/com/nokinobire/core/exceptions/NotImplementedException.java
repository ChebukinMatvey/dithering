package com.nokinobire.core.exceptions;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
        super();
    }

    public NotImplementedException(String s) {
        super(s);
    }

    public NotImplementedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotImplementedException(Throwable throwable) {
        super(throwable);
    }
}
