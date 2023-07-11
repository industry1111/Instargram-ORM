package com.travel.web_oasis.domain.service.Commnet;


import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.repository.CommentRepository;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import com.travel.web_oasis.domain.repository.post.PostRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public Long addComment(CommentDTO commentDTO) {

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .member(getMember(commentDTO.getMemberId()))
                .post(getPost(commentDTO.getPostId()))
                .build();

        commentRepository.save(comment);
        return comment.getId();
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

}
