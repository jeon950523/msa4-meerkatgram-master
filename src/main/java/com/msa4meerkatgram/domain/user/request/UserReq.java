package com.msa4meerkatgram.domain.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserReq(
    @NotBlank(message = "이메일은 필수입니다.")
    @Size(max = 100, message = "100자 이하")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email    
) {
}
