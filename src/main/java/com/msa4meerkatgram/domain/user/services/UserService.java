package com.msa4meerkatgram.domain.user.services;

import com.msa4meerkatgram.domain.auth.repositories.AuthRepository;
import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import com.msa4meerkatgram.global.errors.custom.DuplicatedRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthRepository authRepository;
    
    @Transactional(rollbackFor = Exception.class)
    public String changedEmail(String email, long id){
        authRepository.findByEmail(email).ifPresent(user -> {
            if(user.getId().equals(id)){
                throw new DeletedRecordException("현재 사용중인 이메일입니다.");
            }
            throw new DuplicatedRecordException("이미 사용중인 이메일 입니다.");
        });
        User user = authRepository.findById(id).orElseThrow(()->new DeletedRecordException("회원정보가 없습니다."));
        user.setEmail(email);
        return email;
    }

    
}
