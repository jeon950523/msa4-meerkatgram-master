package com.msa4meerkatgram.domain.auth.mapper;

import com.msa4meerkatgram.domain.user.entities.UserMybatis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    int updateRefreshToken(@Param("id") long id, @Param("refreshToken") String refreshToken);
    int created(UserMybatis user);
}
