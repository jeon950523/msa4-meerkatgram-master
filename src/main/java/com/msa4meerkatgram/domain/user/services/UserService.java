package com.msa4meerkatgram.domain.user.services;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.domain.user.mapper.UserMapper;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import com.msa4meerkatgram.global.errors.custom.DuplicatedRecordException;
import com.msa4meerkatgram.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    
    @Transactional(rollbackFor = Exception.class)
    public String changedEmail(String email, long id){
        User user = userMapper.findByEmail(email);
        if(user!=null ){
        if(user.getId()==(id)){
            throw new DuplicatedRecordException("현재 사용중인 이메일 입니다.");
        }
            throw new DuplicatedRecordException("이미 사용중인 이메일 입니다.");
        }
        int result = userMapper.changeByEmail(email,id);
        if (result==0){
            throw new DeletedRecordException("회원 정보가 없습니다.");
        }
        return email;
    }
}
