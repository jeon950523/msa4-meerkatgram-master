package com.msa4meerkatgram.domain.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "유저 이메일 변경 요청 데이터")
public record UserReq(
    @Schema(description = "변경할 새로운 이메일 주소 (이메일 포맷 필수, 최대 100자)", example = "newmeerkat@gmail.com", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일은 필수입니다.")
    @Size(max = 100, message = "100자 이하")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email    
) {
}
