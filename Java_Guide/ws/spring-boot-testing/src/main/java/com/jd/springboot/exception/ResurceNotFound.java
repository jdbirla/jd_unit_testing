package com.jd.springboot.exception;

/**
 * Created by jd birla on 24-11-2022 at 08:02
 */
public class ResurceNotFound extends RuntimeException{

    public ResurceNotFound() {
    }

    public ResurceNotFound(String message) {
        super(message);
    }

    public ResurceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
