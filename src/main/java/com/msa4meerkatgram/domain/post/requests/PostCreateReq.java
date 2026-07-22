package com.msa4meerkatgram.domain.post.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 작성 요청 데이터")
public record PostCreateReq(
    @Schema(description = "게시글 본문 내용 (최대 1000자)", example = "오늘 날씨가 정말 좋네요! #daily", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "게시글의 내용을 적어주세요.")
    @Size(max = 1000, message = "1000자 이하만 작성가능")
    String content,
    @Schema(description = "업로드 완료된 게시글 본문 이미지 파일명 또는 경로", example = "post_image_123.jpg", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이미지는 필수 입니다.")
    String image
) {
}
