package com.msa4meerkatgram.domain.user.mapper;

import com.msa4meerkatgram.domain.user.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByPk(long id);
    
    User findByEmail(String email);
    
    int changeByEmail(@Param("email") String email,@Param("id") long id);
}
