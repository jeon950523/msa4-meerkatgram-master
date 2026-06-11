package com.msa4meerkatgram.domain.post.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateReq(
    @NotBlank(message = "게시글의 내용을 적어주세요.")
    @Size(max = 1000, message = "1000자 이하만 작성가능")
    String content,
    @NotBlank(message = "이미지는 필수 입니다.")
    String image
) {
}
