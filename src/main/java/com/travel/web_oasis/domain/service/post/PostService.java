package com.travel.web_oasis.domain.service.post;


import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.FileAttach;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.service.fileAttatch.FileAttachServiceImpl;
import com.travel.web_oasis.web.dto.CommentDTO;
import com.travel.web_oasis.web.dto.PageRequestDTO;
import com.travel.web_oasis.web.dto.PageResultDTO;
import com.travel.web_oasis.web.dto.PostDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface PostService {

    Long createPost(PostDTO postDTO, Member member);
    PostDTO findPost(Long id);

    PageResultDTO<PostDTO, Post> getPostList(PageRequestDTO requestDTO, Long id);

    PageResultDTO<PostDTO, Post> getMemberPostList(PageRequestDTO requestDTO, Long memberId);
    void deletePost(Long id);

    Post findById(Long id);

    default Post dtoToEntity(PostDTO postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .fileAttachList(FileAttachServiceImpl.dtosToEntities(postDto.getFiles()))
                .build();
    }

    default PostDTO entityToDto(Long id, String content, LocalDateTime createDate, Member member, List<FileAttach> fileAttachList, List<Comment> commentList) {

        // `fileAttachList`의 각 요소의 `fileName`을 `fileNames` 배열에 추가
        String[] fileNames = fileAttachList.stream().map(FileAttach::getFileName).toArray(String[]::new);

        // `fileAttachList`의 각 요소의 `fileStoreName`을 `fileStoreNames` 배열에 추가
        String[] fileStoreNames = fileAttachList.stream().map(FileAttach::getFileStoreName).toArray(String[]::new);

        // Commet -> CommentDTO로 변환
        List<CommentDTO> commentDTOS = new ArrayList<>();
        if ( commentList != null) {
            for (Comment comment : commentList) {

                Member commentMember = comment.getMember();

                CommentDTO commentDTO = CommentDTO.builder()
                        .content(comment.getContent())
                        .memberId(commentMember.getId())
                        .memberName(commentMember.getName())
                        .memberProfileImg(commentMember.getPicture())
                        .build();
                commentDTOS.add(commentDTO);
            }
        }

        return PostDTO.builder()
                .id(id)
                .content(content)
                .createdDate(createDate)
                .fileNames(fileNames)
                .fileStoreNames(fileStoreNames)
                .memberId(member.getId())
                .name(member.getName())
                .picture(member.getPicture())
                .commentDTOS(commentDTOS)
                .build();
    }
}

