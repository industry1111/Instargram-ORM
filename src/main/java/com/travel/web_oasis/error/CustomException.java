package com.travel.web_oasis.error;

import com.travel.web_oasis.ErrorCode;

public class CustomException extends RuntimeException {

    public CustomException() {
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
    }

}
