package com.msa4meerkatgram.domain.post.services;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.mapper.PostMapper;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostMapper postMapper;

    public PostIndexRes index(PostIndexRequest reqParam){
        int offset = (reqParam.page() - 1) * reqParam.limit();
        
        // 특정 페이지 게시글 조회
        List<Post> posts = postMapper.getPagination(reqParam.limit(), offset);
        
        // 토탈 획득
        long total = postMapper.getTotal();
        boolean lastPage = offset + reqParam.limit() >= total;
        
        
        // 컨트롤러 전달
        return PostIndexRes.builder()
            .total(total)
            .lastPage(lastPage)
            .posts(posts)
            .build();
        
    }
    
    public Post show(long id){
        Post post = postMapper.findByPk(id);
        if (post == null){
            throw new DeletedRecordException("이미 삭제된 게시글 입니다.");
        }
        return post;
    }
}
