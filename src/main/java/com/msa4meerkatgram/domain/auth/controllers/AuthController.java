package com.msa4meerkatgram.domain.auth.controllers;

import com.msa4meerkatgram.domain.auth.requests.LoginReq;
import com.msa4meerkatgram.domain.auth.requests.RegistrationReq;
import com.msa4meerkatgram.domain.auth.responses.AuthRes;
import com.msa4meerkatgram.domain.auth.services.AuthService;
import com.msa4meerkatgram.global.config.openapi.CustomApiResponse;
import com.msa4meerkatgram.global.responses.GlobalRes;
import com.msa4meerkatgram.global.responses.constant.CustomResponseCode;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Tag : API들을 기능별 또는 도메인별로 그룹화 할때 사용
@Tag(name = "인증 API", description = "인증 및 인가 담당 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    
    @Operation(summary = "로그인 처리",description = "이메일과 비밀번호로 로그인")
    @CustomApiResponse(value = {
        CustomResponseCode.INVALID_PARAMETER_ERROR
        , CustomResponseCode.NOT_REGISTERED_ERROR
        , CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR})
    @PostMapping("/login")
    public ResponseEntity<GlobalRes<AuthRes>> login(
        @Valid @RequestBody LoginReq loginReq
        , HttpServletResponse response
        ){
        return ResponseEntity.ok(GlobalRes.success(authService.login(response, loginReq)));
    }
    
    @Operation(summary = "리이슈 처리",description = "토큰 재발급, 엑세스와 리프래시")
    @CustomApiResponse(value = {
        CustomResponseCode.INVALID_TOKEN_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.DB_ERROR
    })
    @PostMapping("/reissue-token")
    public ResponseEntity<GlobalRes<AuthRes>> reissue(
        HttpServletRequest request, HttpServletResponse response
    ){
        return ResponseEntity.ok(GlobalRes.success(authService.reissue(request, response)));
    }
    @Operation(summary = "로그아웃 처리",description = "로그아웃")
    @CustomApiResponse(value = {
        CustomResponseCode.SYS_ERROR
        , CustomResponseCode.DB_ERROR
        , CustomResponseCode.INVALID_TOKEN_ERROR
        , CustomResponseCode.UNAUTHENTICATED_ERROR})
    @PostMapping("/logout")
    public ResponseEntity<GlobalRes<Void>> logout(HttpServletResponse response, @AuthenticationPrincipal Claims claims){
        authService.logout(response, Long.parseLong(claims.getSubject()));

        return ResponseEntity.ok(GlobalRes.success());
    }
    @Operation(summary = "회원가입 처리",description = "이메일과 비밀번호, 프로필 필수")
    @CustomApiResponse(value = {
        CustomResponseCode.DUPLICATED_RECORD_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.DB_ERROR
        , CustomResponseCode.INVALID_PARAMETER_ERROR})
    @PostMapping("/registration")
    public ResponseEntity<GlobalRes<Void>> registration(@Valid @RequestBody RegistrationReq registrationReq){
        authService.registration(registrationReq);

        return ResponseEntity.ok(GlobalRes.success());
    }


}
