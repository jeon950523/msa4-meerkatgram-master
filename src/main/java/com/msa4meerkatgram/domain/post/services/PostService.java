package com.msa4meerkatgram.domain.post.services;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.mapper.PostMapper;
import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import com.msa4meerkatgram.global.errors.custom.PostPermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Post> getMyPosts(long userId){
        return postMapper.getPostsByUserId(userId);
    }
    
    
    
    
    @Transactional(rollbackFor = Exception.class)
    public Post create(PostCreateReq req, long userId){
        Post post = Post.builder()
            .userId(userId)
            .content(req.content())
            .image(req.image())
            .build();
        postMapper.postCreate(post);
        return postMapper.findByPk(post.getId());
        
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(long userId, long id){
       
        Post post = postMapper.findByPk(id);
        
        if(post == null|| post.getDeletedAt() !=null){
            throw new DeletedRecordException("이미 삭제된 게시글 입니다.");
        }
        
        int deleteCount = postMapper.postDelete(id, userId);
        if(deleteCount == 0 ){
            throw new PostPermissionDeniedException("삭제할 권한이 없습니다.");
        }
    }
}
