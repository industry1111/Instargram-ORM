package com.travel.web_oasis.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeBoard is a Querydsl query type for LikeBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeBoard extends EntityPathBase<LikeBoard> {

    private static final long serialVersionUID = -517766599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeBoard likeBoard = new QLikeBoard("likeBoard");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.travel.web_oasis.domain.member.QMember member;

    public final QPost post;

    public QLikeBoard(String variable) {
        this(LikeBoard.class, forVariable(variable), INITS);
    }

    public QLikeBoard(Path<? extends LikeBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeBoard(PathMetadata metadata, PathInits inits) {
        this(LikeBoard.class, metadata, inits);
    }

    public QLikeBoard(Class<? extends LikeBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.travel.web_oasis.domain.member.QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

