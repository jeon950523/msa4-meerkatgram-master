package com.msa4meerkatgram.domain.auth.responses;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.domain.user.responses.UserWithPostCountRes;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 레스폰스")
public record AuthRes(
    @Schema(description = "로그인 완료된 사용자 상세 정보 및 게시글 수")
    UserWithPostCountRes user
    ,@Schema(description = "인증에 사용할 액세스 토큰 (JWT, 헤더의 Authorization 필드에 탑재)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIx...")
    String accessToken
) {
    public static AuthRes from(User user, String accessToken, long countPosts) {
        return new AuthRes(
            UserWithPostCountRes.from(user, countPosts), accessToken
        );
    }
}
