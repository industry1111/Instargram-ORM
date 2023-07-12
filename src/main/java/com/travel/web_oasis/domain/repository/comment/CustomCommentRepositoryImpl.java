package com.travel.web_oasis.domain.repository.comment;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import com.travel.web_oasis.domain.entity.Comment;
import com.travel.web_oasis.domain.entity.QComment;
import com.travel.web_oasis.domain.service.Commnet.CommentDTO;
import com.travel.web_oasis.domain.service.Commnet.QCommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomCommentRepository {

    private final JPQLQueryFactory queryFactory;

    public CustomCommentRepositoryImpl(JPQLQueryFactory queryFactory) {
        super(Comment.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CommentDTO> getCommentsByPostId(Pageable pageable, Long postId) {

        QComment comment = QComment.comment;
        QueryResults<CommentDTO> results = queryFactory
                .select(new QCommentDTO(
                        comment.content,
                        comment.member.id,
                        comment.member.picture,
                        comment.member.name
                ))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommentDTO> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


}
