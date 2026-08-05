package org.example.ecommerce.exception;

import lombok.Getter;
import org.example.ecommerce.enums.ErrorCode;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;


    public ApiException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode, String customMessage ){
        super(customMessage);
        this.errorCode = errorCode;
    }
}
