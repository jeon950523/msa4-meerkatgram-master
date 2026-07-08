package com.msa4meerkatgram.domain.auth.requests;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.security.constant.ProviderPolicy;
import com.msa4meerkatgram.global.security.constant.RolePolicy;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrationReq(
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}@[0-9a-zA-Z](?!.*?[\\-_.]{2})[a-zA-Z0-9\\-_.]{3,63}\\.[a-zA-Z]{2,3}$", message = "허용하지 않는 양식입니다.")
    String email,
    
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z!@#$%^&*()]{8,20}$", message = "허용하지 않는 비밀번호 양식입니다.")
    String password,
    
    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    String passwordChk,
    
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z_]{2,20}$", message = "허용하지 않는 닉네임 양식입니다.")
    String nick,
    
    @NotBlank(message = "프로필은 필수 항목입니다.")
    String profile
) {
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
