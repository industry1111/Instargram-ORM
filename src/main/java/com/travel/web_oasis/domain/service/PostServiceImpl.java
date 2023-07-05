package com.travel.web_oasis.domain.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.repository.PostRepository;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import com.travel.web_oasis.web.dto.PostDTO;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final MemberService memberService;

    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    @Transactional
    public Long createPost(PostDTO postDTO, Member member) {

        logger.info("createPost() called   PostDTO : {}, member : {}", postDTO, member);

        Post post = dtoToEntity(postDTO);
        post.setMember(member);

        Long id = postRepository.save(post).getId();

        return id;
    }

    @Override
    public PostDTO findPost(Long id) {

        Post post =  postRepository.findById(id).orElseThrow(null);

        if (post == null) {
            return null;
        }

        return entityToDto(post.getId(), post.getContent(), post.getMember(), post.getFileAttachList());

    }

    @Override
    public void deletePost(Long id) {
        //삭제할 포스트
//        PostDTO removePostDTO = getPost(id);
        //삭제할 포스트의 첨부파일들
//        List<FileAttach> files = removePostDTO.getFiles();
//        System.out.println("files = " + files);
//        for (FileAttach file : files) {
//
//            log.info("deletePost() called   file : {}", file.toString());
//
//        }
//        //저장소에서 첨부파일들 삭제
//        fileAttachService.deleteFiles(files);
//        //포스트 삭제
//        postRepository.deleteById(id);
    }


    @Override
    public PageResultDTO<PostDTO, Post> getPostList(PageRequestDTO requestDTO, Long id) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        Page<Post> result = postRepository.findAll(pageable);

        result.stream().forEach(post -> System.out.println("post = " + post));

        Function<Post, PostDTO> fn = (entity -> entityToDto(entity.getId(), entity.getContent(), entity.getMember(), entity.getFileAttachList()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<PostDTO, Post> getMemberPostList(PageRequestDTO requestDTO, Long memberId) {

        QMember qMember = QMember.member;

        Pageable pageable = requestDTO.getPageable(Sort.by("id"));

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exMemberId = qMember.id.eq(memberId);

        builder.and(exMemberId);

        Page<Post> result = postRepository.findAll(builder,pageable);

        result.stream().forEach(post -> System.out.println("post = " + post));

        Function<Post, PostDTO> fn = (entity -> entityToDto(entity.getId(), entity.getContent(), entity.getMember(), entity.getFileAttachList()));

        return new PageResultDTO<>(result,fn);
    }
}
