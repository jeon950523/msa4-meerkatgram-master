package com.msa4meerkatgram.domain.post.responses;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "게시글 목록 페이징 응답 데이터")
public record PostIndexRes(
        @Schema(description = "전체 게시글 개수", example = "100")
        Long total
        , @Schema(description = "마지막 페이지 여부 (더 이상 가져올 데이터가 없을 때 true)", example = "false")
        boolean lastPage
        , @Schema(description = "현재 페이지에 소속된 게시글 목록")
        List<PostWithUserRes> posts
) {
    public static PostIndexRes from(
        Long total
        , boolean lastPage
        , List<Post> posts) 
    {
        return new PostIndexRes(total, lastPage, posts.stream().map(PostWithUserRes::from).toList());
    }
}
