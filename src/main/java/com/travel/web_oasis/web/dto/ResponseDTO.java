package com.travel.web_oasis.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class ResponseDTO<T> {

    private int status;
    private String message;
    private T data;

    @Builder
    public ResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
