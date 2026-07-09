package com.msa4meerkatgram.global.annotations.openapi;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 요소타입
@Retention(RetentionPolicy.RUNTIME) // 어느시점에 동작할지
@ApiResponse(responseCode = "401", description = "토큰 이상"
    ,content = @Content(
        mediaType = "application/json"
        , examples = {
            @ExampleObject(name = "유효성 검사 실패 에러",summary = "이메일이 없거나 비밀번호가 틀린 경우",value = "{\"code\":\"E01\",\"message\":\"로그인 실패\"}"
           ),
            @ExampleObject(name = "인증이 필요한 서비스",summary = "헤더에 토큰이 누락된 경우",value = """
                {"code":"E02","message":"토큰의 부재 또는 토큰 비정상"
                }
                """
            ),
            @ExampleObject(name = "유효하지 않은 토큰",summary = "토큰이 만료되었거나 서명이 손상된경우",value = """
                {"code":"E04","message":"토큰이 만료되었습니다."
                }
                """
    ),
        }   
    )
)
public @interface ApiUnauthorizedErrorResponse {
    
}
