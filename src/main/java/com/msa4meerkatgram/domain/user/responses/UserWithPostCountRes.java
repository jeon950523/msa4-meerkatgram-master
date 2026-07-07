package com.msa4meerkatgram.domain.user.responses;

import com.msa4meerkatgram.domain.user.entities.User;

public record UserWithPostCountRes(
   UserRes user,
   Long countPosts
) {
    public static UserWithPostCountRes from(User user, Long countPosts) {
        return new UserWithPostCountRes(
            UserRes.from(user),
            countPosts
        );
    }
}
