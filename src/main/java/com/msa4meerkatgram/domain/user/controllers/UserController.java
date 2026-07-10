package com.msa4meerkatgram.domain.user.controllers;

import com.msa4meerkatgram.domain.user.request.UserReq;
import com.msa4meerkatgram.domain.user.services.UserService;
import com.msa4meerkatgram.global.config.openapi.CustomApiResponse;
import com.msa4meerkatgram.global.responses.GlobalRes;
import com.msa4meerkatgram.global.responses.constant.CustomResponseCode;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 정보 변경API", description = "유저의 개인 정보들을 변경")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    @Operation(summary = "이메일 변경", description = "현재 로그인한 회원의 로그인용 이메일 주소를 중복 검사 후 안전하게 변경합니다.")
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR
        , CustomResponseCode.DUPLICATED_RECORD_ERROR})
    @PatchMapping("/users/email")
    public ResponseEntity<GlobalRes<String>>changeUserEmail(@AuthenticationPrincipal Claims claims, @Valid @RequestBody UserReq userReq){
        long userId = Long.parseLong(claims.getSubject());
        String changeEmail = userService.changedEmail(userReq.email(), userId);

        return ResponseEntity.ok(GlobalRes.success(changeEmail));
    }
}
