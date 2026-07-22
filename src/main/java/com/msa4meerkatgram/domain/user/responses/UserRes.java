package com.msa4meerkatgram.domain.user.responses;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.security.constant.RolePolicy;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "사용자 기본 정보 응답")
public record UserRes(
    @Schema(description = "사용자 고유 ID 번호", example = "1")
    long id 
     , @Schema(description = "사용자 이메일 주소", example = "meerkat@gmail.com")
     String email
     , @Schema(description = "사용자 닉네임", example = "meerkat_gram")
     String nick
     , @Schema(description = "사용자 권한 등급 (NORMAL, ADMIN 등)")
     RolePolicy role
     , @Schema(description = "사용자 프로필 이미지 경로", example = "/uploads/profiles/profile_default.png")
     String profile
     , @Schema(description = "사용자 가입 시간", example = "2026-07-10T12:00:00")
     LocalDateTime createdAt
) {
    public static UserRes from(User user) {
        return new UserRes(
            user.getId(),
            user.getEmail(),
            user.getNick(),
            user.getRole(),
            user.getProfile(),
            user.getCreatedAt()
        );
    }
}
