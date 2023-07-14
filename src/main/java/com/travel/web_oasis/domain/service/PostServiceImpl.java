package com.travel.web_oasis.domain.service;

import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.domain.repository.post.PostRepository;
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
import org.webjars.NotFoundException;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    private final FileAttachService fileAttachService;

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

        return entityToDto(post.getId(), post.getContent(),post.getCreatedDate(), post.getMember(), post.getFileAttachList(), post.getCommentList());

    }

    @Override
    public void deletePost(Long id) {
//    삭제할 포스트
        PostDTO removePostDTO = findPost(id);
//    삭제할 포스트의 첨부파일들
        String[] fileStoreNames = removePostDTO.getFileStoreNames();

        for (String fileStoreName : fileStoreNames) {

            logger.info("deletePost() called   file : {}", fileStoreName);

        }
        //저장소에서 첨부파일들 삭제
        fileAttachService.deleteFiles(fileStoreNames);
        //포스트 삭제
        postRepository.deleteById(id);
    }

    @Override
    public PageResultDTO<PostDTO, Post> getPostList(PageRequestDTO requestDTO, Long id) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        Page<Post> result = postRepository.findAll(pageable);

        Function<Post, PostDTO> fn = (entity -> entityToDto(entity.getId(), entity.getContent(), entity.getCreatedDate(), entity.getMember(), entity.getFileAttachList(), entity.getCommentList()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<PostDTO, Post> getMemberPostList(PageRequestDTO requestDTO, Long memberId) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        Page<Post> result = postRepository.gePostsByMemberId(pageable,memberId);

        Function<Post, PostDTO> fn = (entity -> entityToDto(entity.getId(), entity.getContent(),entity.getCreatedDate(), entity.getMember(), entity.getFileAttachList(), entity.getCommentList()));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다. id =" + id));
    }
}

