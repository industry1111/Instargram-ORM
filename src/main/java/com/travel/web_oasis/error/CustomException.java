package com.travel.web_oasis.error;

public class CustomException extends RuntimeException {

    public CustomException() {
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
    }

}
