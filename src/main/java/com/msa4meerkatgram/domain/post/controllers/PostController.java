package com.msa4meerkatgram.domain.post.controllers;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.domain.post.services.PostService;
import com.msa4meerkatgram.global.responses.GlobalRes;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor 
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<GlobalRes<PostIndexRes>> index(PostIndexRequest req) {
        PostIndexRes result = postService.index(req);

        return ResponseEntity.status(200).body(
            GlobalRes.<PostIndexRes>builder()
                .code("00")
                .message("정상 처리")
                .data(result)
                .build());

    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<Post>> show(
        @Min(value = 1, message = "1 이상 숫자만 허용합니다.") @PathVariable long id
    ) {
        Post result = postService.show(id);

        return ResponseEntity.status(200).body(
            GlobalRes.<Post>builder()
                .code("00")
                .message("게시글 상세")
                .data(result)
                .build()
        );
    }

    @PostMapping("/posts")
    public ResponseEntity<GlobalRes<Post>> postCreate(@Valid @RequestBody PostCreateReq req,@AuthenticationPrincipal Claims claims) {
    long userId = Long.parseLong(claims.getSubject());
    Post result = postService.create(req, userId);
        return ResponseEntity.status(200).body(
            GlobalRes.<Post>builder()
                .code("00")
                .message("게시글 작성 완료")
                .data(result)
                .build());
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<String>> postDelete(
        @AuthenticationPrincipal Claims claims, @Min(value = 1, message = "1이상의 숫자만 허용됩니다.") @PathVariable long id ) {
       
        long userId = Long.parseLong(claims.getSubject());
        postService.delete(userId, id);
        
        return ResponseEntity.status(200).body(
            GlobalRes.<String>builder()
                .code("00")
                .message("게시글 삭제 완료")
                .data("게시글이 삭제 되었습니다.")
                .build());
    }
    
    @GetMapping("/posts/my")
    public ResponseEntity<GlobalRes<List<Post>>> myPosts(
        @AuthenticationPrincipal Claims claims
    ){
        long userId = Long.parseLong(claims.getSubject());
        List<Post> result = postService.getMyPosts(userId);
        
        return ResponseEntity.status(200).body(
            GlobalRes.<List<Post>>builder()
                .code("00")
                .message("내가 쓴 게시글 조회 완료")
                .data(result)
                .build()
        );
    }
}
