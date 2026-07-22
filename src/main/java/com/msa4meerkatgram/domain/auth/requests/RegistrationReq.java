package com.msa4meerkatgram.domain.auth.requests;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.security.constant.ProviderPolicy;
import com.msa4meerkatgram.global.security.constant.RolePolicy;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원가입시 필요한 데이터")
public record RegistrationReq(
    
    @Schema(description = "회원 가입용 이메일 주소 (이메일 포맷 필수)", example = "meerkat@gmail.com", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}@[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}\\.[a-zA-Z]{2,3}$", message = "허용하지 않는 양식입니다.")
    String email,
    
    @Schema(description = "비밀번호 (영문, 숫자, 특수문자 조합 8~20자)", example = "securePass123!", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z!@#$%^&*()]{8,20}$", message = "허용하지 않는 비밀번호 양식입니다.")
    String password,
    
    @Schema(description = "비밀번호 확인 (입력한 비밀번호와 반드시 일치해야 함)", example = "securePass123!", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    String passwordChk,
    
    @Schema(description = "사용자 닉네임 (영문, 숫자, 언더바 조합 2~20자)", example = "meerkat_gram", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z_]{2,20}$", message = "허용하지 않는 닉네임 양식입니다.")
    String nick,
    
    @Schema(description = "프로필 이미지 파일명 또는 경로", example = "profile_meerkat.png", nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "프로필은 필수 항목입니다.")
    String profile
) {
    @Schema(hidden = true)
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordMatch(){
        if (this.password == null || this.passwordChk == null){
            return false;
        }
        return this.password.equals(this.passwordChk);
    }
    public User toEntity(String encodePassword){
        User newUser = new User();
        newUser.setEmail(this.email);
        newUser.setPassword(encodePassword);
        newUser.setNick(this.nick);
        newUser.setProfile(this.profile);
        newUser.setProvider(ProviderPolicy.NONE);
        newUser.setRole(RolePolicy.NORMAL);
        newUser.setRefreshToken(null);
        return newUser;
    }
    
}
