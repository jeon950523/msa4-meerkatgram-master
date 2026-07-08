package com.msa4meerkatgram.domain.post.responses;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;

import java.util.List;

public record PostIndexRes(
        Long total
        , boolean lastPage
        , List<PostWithUserRes> posts
) {
    public static PostIndexRes from(
        Long total
        , boolean lastPage
        , List<Post> posts) 
    {
        return new PostIndexRes(total, lastPage, posts.stream().map(PostWithUserRes::from).toList());
    }
}
