package com.msa4meerkatgram.domain.post.services;

import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.repositories.PostQueryRepository;
import com.msa4meerkatgram.domain.post.repositories.PostRepository;
import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.domain.post.responses.PostWithUserRes;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import com.msa4meerkatgram.global.errors.custom.PostPermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    
    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;

    public PostIndexRes index(PostIndexRequest reqParam){
        int offset = (reqParam.page() - 1) * reqParam.limit();

        // 특정 페이지 게시글 조회
        List<Post> result = postQueryRepository.paginationByCreatedAtDescIdAsc(reqParam.limit(), offset); 
            
        // 토탈 획득
        long total = postRepository.count();
        boolean lastPage = offset + reqParam.limit() >= total;


        // 컨트롤러 전달
        return PostIndexRes.from(total, lastPage, result);

    }

    @Transactional(readOnly = true)
    public PostWithUserRes show(long id) {
        Post result = postRepository.findById(id)
            .orElseThrow(() -> new DeletedRecordException("이미 삭제된 게시글입니다."));

        return PostWithUserRes.from(result);
    }

//    @Transactional(rollbackFor = Exception.class)
//    public PostMybatis create(PostCreateReq req, long userId){       
//        PostMybatis post = PostMybatis.builder()
//            .userId(userId)
//            .content(req.content())
//            .image(req.image())
//            .build();
//        postMapper.postCreate(post);
//        return postMapper.findByPk(post.getId());
//
//    }
//    @Transactional(rollbackFor = Exception.class)
//    public void delete(long userId, long id){
//
//        PostMybatis post = postMapper.findByPk(id);
//
//        if(post == null|| post.getDeletedAt() !=null){
//            throw new DeletedRecordException("이미 삭제된 게시글 입니다.");
//        }
//
//        int deleteCount = postMapper.postDelete(id, userId);
//        if(deleteCount == 0 ){
//            throw new PostPermissionDeniedException("삭제할 권한이 없습니다.");
//        }
//    }
}
