package com.msa4meerkatgram.domain.post.services;

import com.msa4meerkatgram.domain.auth.repositories.AuthRepository;
import com.msa4meerkatgram.domain.post.entities.Post;
import com.msa4meerkatgram.domain.post.repositories.PostQueryRepository;
import com.msa4meerkatgram.domain.post.repositories.PostRepository;
import com.msa4meerkatgram.domain.post.requests.PostCreateReq;
import com.msa4meerkatgram.domain.post.requests.PostIndexRequest;
import com.msa4meerkatgram.domain.post.responses.PostIndexRes;
import com.msa4meerkatgram.domain.post.responses.PostWithUserRes;
import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.errors.custom.DeletedRecordException;
import com.msa4meerkatgram.global.errors.custom.PostPermissionDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final AuthRepository authRepository;

    public PostIndexRes index(PostIndexRequest reqParam) {
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

    @Transactional(rollbackFor = Exception.class)
    public PostWithUserRes create(PostCreateReq req, long userId) {
        User user = authRepository.findById(userId).orElseThrow(() -> new DeletedRecordException("존재하지않는 회원입니다."));

        Post post = new Post();
        post.setContent(req.content());
        post.setImage(req.image());
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return PostWithUserRes.from(savedPost);

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(long userId, long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new DeletedRecordException("이미 삭제되었거나 존재하지 않는 게시글 입니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new PostPermissionDeniedException("삭제할 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<PostWithUserRes> myPosts(long userId) {
        List<Post> result = postRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        return result.stream()
            .map(PostWithUserRes::from)
            .collect(Collectors.toList());
    }
}
