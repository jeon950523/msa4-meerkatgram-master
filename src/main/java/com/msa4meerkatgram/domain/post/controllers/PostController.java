package com.msa4meerkatgram.domain.post.controllers;

import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.domain.post.responses.PostWithUserRes;
import com.msa4meerkatgram.domain.post.services.PostService;
import com.msa4meerkatgram.global.annotations.openapi.ApiNotValidErrorResponse;
import com.msa4meerkatgram.global.annotations.openapi.ApiUnauthorizedErrorResponse;
import com.msa4meerkatgram.global.responses.GlobalRes;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 API",description = "회원의 게시글 조회 및 작성과 삭제")
@Validated
@RequiredArgsConstructor 
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    
    @ApiResponse(responseCode = "200", description = "게시글 목록 획득 성공")
    @ApiNotValidErrorResponse
    @Operation(summary = "게시글 조회",description = "작성된 전체 게시글 조회")
    @GetMapping("/posts")
    public ResponseEntity<GlobalRes<PostIndexRes>> index(PostIndexRequest req) {
        PostIndexRes result = postService.index(req);

        return ResponseEntity.ok(GlobalRes.success(result));

    }
    @Operation(summary = "게시글 상세 조회",description = "선택한 게시글의 정보를 상세조회")
    @ApiUnauthorizedErrorResponse
    @GetMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<PostWithUserRes>> show(
        @Parameter(description = "게시글 번호", example = "1") @Min(value = 1, message = "1 이상 숫자만 허용합니다.") @PathVariable long id
    ) {
        PostWithUserRes result = postService.show(id);

        return ResponseEntity.ok(GlobalRes.success(result));
    }

    @PostMapping("/posts")
    @ApiUnauthorizedErrorResponse
    public ResponseEntity<GlobalRes<PostWithUserRes>> postCreate(@Valid @RequestBody PostCreateReq req, @AuthenticationPrincipal Claims claims) {
    long userId = Long.parseLong(claims.getSubject());
        PostWithUserRes result = postService.create(req, userId);
        return ResponseEntity.ok(GlobalRes.success(result));
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<Void>> postDelete(
        @AuthenticationPrincipal Claims claims, @Min(value = 1, message = "1이상의 숫자만 허용됩니다.") @PathVariable long id ) {

        long userId = Long.parseLong(claims.getSubject());
        postService.delete(userId, id);

        return ResponseEntity.ok(GlobalRes.success());
    }

    @GetMapping("/posts/my")
    public ResponseEntity<GlobalRes<List<PostWithUserRes>>> myPosts(
        @AuthenticationPrincipal Claims claims
    ){
        long userId = Long.parseLong(claims.getSubject());
        List<PostWithUserRes> result = postService.myPosts(userId);

        return ResponseEntity.ok(GlobalRes.success(result));
    }
}
