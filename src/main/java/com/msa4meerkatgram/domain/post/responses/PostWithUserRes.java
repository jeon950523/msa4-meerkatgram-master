package com.msa4meerkatgram.domain.post.responses;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.domain.user.responses.UserRes;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(description = "작성자 정보를 포함한 게시글 상세 정보 응답")
public record PostWithUserRes(
    @Schema(description = "게시글 고유 번호 (ID)", example = "1")
    Long id,
    @Schema(description = "게시글 내용", example = "오늘 점심은 맛있는 돈까스! #yummy")
    String content,
    @Schema(description = "게시글 이미지 파일 경로", example = "/uploads/posts/post_123.jpg")
    String image,
    @Schema(description = "게시글 최초 생성 시간", example = "2026-07-10T12:00:00")
    LocalDateTime createdAt,
    @Schema(description = "게시글 최종 수정 시간", example = "2026-07-10T12:30:00")
    LocalDateTime updatedAt,
    @Schema(description = "작성자의 회원 고유 번호 (ID)", example = "10")
    Long userId,
    @Schema(description = "작성자의 닉네임", example = "yummy_man")
    String userNick,
    @Schema(description = "작성자의 프로필 이미지 경로", example = "/uploads/profiles/user_profile.png")
    String userProfile,
    @Schema(description = "작성자의 상세 정보")
    UserRes user
) {
    public static PostWithUserRes from(Post post) {
        User user = post.getUser();

        return new PostWithUserRes(
            post.getId(),
            post.getContent(),
            post.getImage(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            user.getId(),
            user.getNick(),
            user.getProfile(),
            UserRes.from(user)
        );
    }
}
