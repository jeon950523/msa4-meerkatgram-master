package com.msa4meerkatgram.domain.post.controllers;

import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.domain.post.responses.PostWithUserRes;
import com.msa4meerkatgram.domain.post.services.PostService;
import com.msa4meerkatgram.global.config.openapi.CustomApiResponse;
import com.msa4meerkatgram.global.responses.GlobalRes;
import com.msa4meerkatgram.global.responses.constant.CustomResponseCode;
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
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR})
    @Operation(summary = "게시글 조회",description = "작성된 전체 게시글 조회")
    @GetMapping("/posts")
    public ResponseEntity<GlobalRes<PostIndexRes>> index(PostIndexRequest req) {
        PostIndexRes result = postService.index(req);

        return ResponseEntity.ok(GlobalRes.success(result));

    }
    @Operation(summary = "게시글 상세 조회",description = "선택한 게시글의 정보를 상세조회")
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR
        , CustomResponseCode.INVALID_PARAMETER_ERROR})
    @GetMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<PostWithUserRes>> show(
        @Parameter(description = "게시글 번호", example = "1") @Min(value = 1, message = "1 이상 숫자만 허용합니다.") @PathVariable long id
    ) {
        PostWithUserRes result = postService.show(id);

        return ResponseEntity.ok(GlobalRes.success(result));
    }

    @Operation(summary = "게시글 작성", description = "로그인한 유저가 새로운 게시글을 작성하고 서버에 저장합니다.")
    @PostMapping("/posts")
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR
    , CustomResponseCode.UNAUTHORIZED_ERROR
    , CustomResponseCode.INVALID_PARAMETER_ERROR
    , CustomResponseCode.INVALID_TOKEN_ERROR})
    public ResponseEntity<GlobalRes<PostWithUserRes>> postCreate(@Valid @RequestBody PostCreateReq req, @AuthenticationPrincipal Claims claims) {
    long userId = Long.parseLong(claims.getSubject());
        PostWithUserRes result = postService.create(req, userId);
        return ResponseEntity.ok(GlobalRes.success(result));
    }
    @Operation(summary = "게시글 삭제", description = "내가 쓴 게시글을 소프트 딜리트 형태로 삭제 처리합니다.")
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR
        , CustomResponseCode.UNAUTHORIZED_ERROR
        ,CustomResponseCode.INVALID_TOKEN_ERROR
    })
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<GlobalRes<Void>> postDelete(
        @AuthenticationPrincipal Claims claims, @Min(value = 1, message = "1이상의 숫자만 허용됩니다.") @PathVariable long id ) {

        long userId = Long.parseLong(claims.getSubject());
        postService.delete(userId, id);

        return ResponseEntity.ok(GlobalRes.success());
    }
    
    @Operation(summary = "내가 쓴 게시글 조회", description = "현재 로그인한 유저 본인이 작성한 전체 게시글 목록을 조회합니다.")
    @CustomApiResponse(value = {
        CustomResponseCode.DB_ERROR
        , CustomResponseCode.SYS_ERROR
        , CustomResponseCode.NOT_FOUND_DATA_ERROR
        , CustomResponseCode.UNAUTHORIZED_ERROR
        ,CustomResponseCode.INVALID_TOKEN_ERROR})
    @GetMapping("/posts/my")
    public ResponseEntity<GlobalRes<List<PostWithUserRes>>> myPosts(
        @AuthenticationPrincipal Claims claims
    ){
        long userId = Long.parseLong(claims.getSubject());
        List<PostWithUserRes> result = postService.myPosts(userId);

        return ResponseEntity.ok(GlobalRes.success(result));
    }
}
