package com.msa4meerkatgram.global.errors.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {
    NOT_REGISTERED_ERROR(HttpStatus.UNAUTHORIZED, "E01")
    ,UNAUTHENTICATED_ERROR(HttpStatus.UNAUTHORIZED, "E02")
    ,UNAUTHORIZED_ERROR(HttpStatus.FORBIDDEN, "E03")
    ,INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E04")
    ,INVALID_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "E05")
    ,DUPLICATED_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "E06")
    ,DUPLICATED_USERNAME_ERROR(HttpStatus.BAD_REQUEST, "E07")
    ,NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E08")
    ,NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "E09")
    ,NOT_FOUND_DATA_ERROR(HttpStatus.NOT_FOUND, "E10")
    ,DUPLICATED_RECORD_ERROR(HttpStatus.CONFLICT, "E11")
    ,INVALID_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "E21")
    ,FILE_MANAGED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E40")
    ,DB_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E80")
    ,SYS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E99")
    ;
    private final HttpStatus httpStatus;
    private final String code;
    
    CustomErrorCode(HttpStatus httpStatus, String code){
        this.httpStatus = httpStatus;
        this.code = code;
    }
    
}
