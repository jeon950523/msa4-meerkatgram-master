package com.msa4meerkatgram.global.errors;


import com.msa4meerkatgram.global.errors.constant.CustomErrorCode;
import com.msa4meerkatgram.global.errors.custom.*;
import com.msa4meerkatgram.global.responses.GlobalErrorRes;
import com.msa4meerkatgram.global.responses.GlobalRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ResponseEntity<GlobalErrorRes> generateErrorResponse(CustomErrorCode customErrorCode){
        return ResponseEntity.status(customErrorCode.getHttpStatus())
            .body(GlobalErrorRes.from(customErrorCode.getCode(), customErrorCode.name()));
    };
    @ExceptionHandler(NotRegisteredException.class)
    public ResponseEntity<GlobalErrorRes> notRegisteredHandle(NotRegisteredException e){
        log.debug(CustomErrorCode.NOT_REGISTERED_ERROR.name(),e);
        return this.generateErrorResponse(CustomErrorCode.NOT_REGISTERED_ERROR);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GlobalErrorRes> authenticationHandle(AuthenticationException e){
        return this.generateErrorResponse(CustomErrorCode.UNAUTHENTICATED_ERROR);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GlobalErrorRes> accessDeniedHandle(AccessDeniedException e){
        return this.generateErrorResponse(CustomErrorCode.UNAUTHORIZED_ERROR);
    }
    @ExceptionHandler(PostPermissionDeniedException.class)
    public ResponseEntity<GlobalErrorRes> PostPermissionDeniedExceptionHandle(PostPermissionDeniedException e){
        return this.generateErrorResponse(CustomErrorCode.UNAUTHORIZED_ERROR);
    }
    
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GlobalErrorRes> InvalidTokenHandle(InvalidTokenException e){
        return this.generateErrorResponse(CustomErrorCode.INVALID_TOKEN_ERROR);
    }
    @ExceptionHandler(DeletedRecordException.class)
    public ResponseEntity<GlobalErrorRes> deletedRecordExceptionHandle(DeletedRecordException e){
        return this.generateErrorResponse(CustomErrorCode.NOT_FOUND_DATA_ERROR);
    }
    @ExceptionHandler(DuplicatedRecordException.class)
    public ResponseEntity<GlobalErrorRes> duplicatedRecordHandle(DuplicatedRecordException e){
        return this.generateErrorResponse(CustomErrorCode.DUPLICATED_RECORD_ERROR);
    }
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorRes> methodArgumentNotValidHandle(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField, // 필드명
                fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "유효하지 않은 값입니다.",
                (existing, replacement) -> existing // 중복 필드가 있을 경우 기존 값 유지
            ));

        log.debug(CustomErrorCode.INVALID_PARAMETER_ERROR.name(),errors);
        return this.generateErrorResponse(CustomErrorCode.INVALID_PARAMETER_ERROR);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalErrorRes> methodArgumentTypeMismatchHandle(MethodArgumentTypeMismatchException e){
        log.debug(CustomErrorCode.INVALID_PARAMETER_ERROR.name(),String.format("%s : 필드를 확인해 주세요",e.getName()));
        return this.generateErrorResponse(CustomErrorCode.INVALID_PARAMETER_ERROR);
    }
    @ExceptionHandler(FileManagedException.class)
    public ResponseEntity<GlobalErrorRes> fileManagedExceptionHandle(FileManagedException e){
        log.debug(CustomErrorCode.FILE_MANAGED_ERROR.name(),e);
        return this.generateErrorResponse(CustomErrorCode.FILE_MANAGED_ERROR);
    }
   
    
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<GlobalErrorRes> sqlHandle(SQLException e){
        log.error("DB에러: ", e);
        return this.generateErrorResponse(CustomErrorCode.DB_ERROR);
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<GlobalErrorRes> DatabaseInsertHandle(DataAccessException e){
        log.error("DB 인서트에러: ", e);
        return this.generateErrorResponse(CustomErrorCode.DB_ERROR);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorRes> othersHandle(Exception e){
        log.error("시스템에러: ", e);
        return this.generateErrorResponse(CustomErrorCode.SYS_ERROR);
    }
}
