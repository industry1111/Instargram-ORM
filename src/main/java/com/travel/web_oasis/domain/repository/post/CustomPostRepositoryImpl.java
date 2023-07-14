package com.travel.web_oasis.domain.repository.post;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.entity.QComment;
import com.travel.web_oasis.domain.entity.QFileAttach;
import com.travel.web_oasis.domain.entity.QPost;
import com.travel.web_oasis.domain.member.QMember;
import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
import com.travel.web_oasis.domain.service.Commnet.QCommentDTO;
import com.travel.web_oasis.web.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {
    private final JPQLQueryFactory queryFactory;


    public CustomPostRepositoryImpl(JPQLQueryFactory queryFactory) {
        super(Post.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<PostDTO> getPostList(Pageable pageable, Long memberId) {
//        QPost post = new QPost("post");
//        QComment comment = new QComment("comment");
//        QFileAttach fileAttach = new QFileAttach("fileAttach");
//
//        List<Tuple> result = queryFactory
//                .select(
//                        post.id,
//                        post.content,
//                        new QMemberDTO(
//                          post.member.id,
//                          post.member.name,
//                          post.member.picture,
//                          post.member.introduction
//                        ),
//                        new QFileAttachDTO(
//                                fileAttach.fileName,
//                                fileAttach.fileStoreName
//                        ),
//                        new QCommentDTO(
//                                comment.content,
//                                comment.member.id,
//                                comment.member.picture,
//                                comment.member.name
//                        )
//                )
//                .from(post)
//                .leftJoin(post.commentList,comment)
//                .on(comment.post.id.eq(post.id))
//                .leftJoin(post.fileAttachList,fileAttach)
//                .on(fileAttach.post.id.eq(post.id))
//                .fetch();
//
//        Map<Long,PostDTO> postDTOMap = new HashMap<>();
//        for (Tuple tuple : result) {
//            Long postId = tuple.get(post.id);
//            String content = tuple.get(post.content);
//            MemberDTO memberDTO = tuple.get(2, MemberDTO.class);
//            FileAttachDTO fileAttachDTO = tuple.get(3, FileAttachDTO.class);
//            CommentDTO commentDTO = tuple.get(4, CommentDTO.class);
//            PostDTO postDTO = postDTOMap.get(postId);
//            if (postDTO != null) {
////                postDTO.getFiles().add(fileAttachDTO);
//                postDTO.getCommentDTOS().add(commentDTO);
//            } else {
//
//                List<FileAttachDTO> fileAttachDTOS = new ArrayList<>();
//                fileAttachDTOS.add(fileAttachDTO);
//
//                List<CommentDTO> commentDTOS = new ArrayList<>();
//                commentDTOS.add(commentDTO);
//
//                postDTO = PostDTO.builder()
//                        .id(postId)
//                        .content(content)
//                        .memberDTO(memberDTO)
//                        .files(fileAttachDTOS)
//                        .commentDTOS(commentDTOS)
//                        .build();
//                postDTOMap.put(postId,postDTO);
//            }
//        }
//        List<PostDTO> postDTOList = new ArrayList<>();
//        for (Long key : postDTOMap.keySet()) {
//            postDTOList.add(postDTOMap.get(key));
//        }
//
        return null;
    }

    @Override
    public Page<Post> gePostsByMemberId(Pageable pageable, Long memberId) {
        QPost post = new QPost("post");
        QueryResults<Post> results = queryFactory
                .selectFrom(post)
                .where(post.member.id.eq(memberId))
                .fetchResults();

        List<Post> content = results.getResults();
        long total = results.getTotal();

        return  new PageImpl<>(content,pageable,total);
    }

}
