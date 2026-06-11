package com.msa4meerkatgram.domain.user.controllers;

import com.msa4meerkatgram.domain.user.request.UserReq;
import com.msa4meerkatgram.domain.user.services.UserService;
import com.msa4meerkatgram.global.responses.GlobalRes;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    @PatchMapping("/users/email")
    public ResponseEntity<GlobalRes<String>>changeUserEmail(@AuthenticationPrincipal Claims claims, @Valid @RequestBody UserReq userReq){
        long userId = Long.parseLong(claims.getSubject());
        String changeEmail = userService.changedEmail(userReq.email(), userId);
        
        return ResponseEntity.status(200).body(
            GlobalRes.<String>builder()
                .code("00")
                .message("이메일 변경 완료")
                .data(changeEmail)
                .build()
        );
    }
}
