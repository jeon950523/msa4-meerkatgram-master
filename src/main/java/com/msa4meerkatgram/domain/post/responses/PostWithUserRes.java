package com.msa4meerkatgram.domain.post.responses;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.domain.user.responses.UserRes;

import java.time.LocalDateTime;


public record PostWithUserRes(
    Long id,
    String content,
    String image,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long userId,
    String userNick,
    String userProfile,
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
