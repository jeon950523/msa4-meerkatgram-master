package com.msa4meerkatgram.domain.user.responses;

import com.msa4meerkatgram.domain.user.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보와 작성한 게시글의 총 개수 응답")
public record UserWithPostCountRes(
   @Schema(description = "사용자 기본 상세 정보")
   UserRes user,
   @Schema(description = "사용자가 작성한 전체 게시글 개수", example = "5")
   Long countPosts
) {
    public static UserWithPostCountRes from(User user, Long countPosts) {
        return new UserWithPostCountRes(
            UserRes.from(user),
            countPosts
        );
    }
}
