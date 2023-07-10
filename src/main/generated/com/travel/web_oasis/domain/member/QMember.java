package com.travel.web_oasis.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 522746777L;

    public static final QMember member = new QMember("member1");

    public final com.travel.web_oasis.domain.entity.QBaseEntity _super = new com.travel.web_oasis.domain.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final ListPath<com.travel.web_oasis.domain.entity.Follow, com.travel.web_oasis.domain.entity.QFollow> fromFollows = this.<com.travel.web_oasis.domain.entity.Follow, com.travel.web_oasis.domain.entity.QFollow>createList("fromFollows", com.travel.web_oasis.domain.entity.Follow.class, com.travel.web_oasis.domain.entity.QFollow.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final ListPath<com.travel.web_oasis.domain.entity.LikeBoard, com.travel.web_oasis.domain.entity.QLikeBoard> LikeBoardList = this.<com.travel.web_oasis.domain.entity.LikeBoard, com.travel.web_oasis.domain.entity.QLikeBoard>createList("LikeBoardList", com.travel.web_oasis.domain.entity.LikeBoard.class, com.travel.web_oasis.domain.entity.QLikeBoard.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final ListPath<com.travel.web_oasis.domain.entity.Post, com.travel.web_oasis.domain.entity.QPost> posts = this.<com.travel.web_oasis.domain.entity.Post, com.travel.web_oasis.domain.entity.QPost>createList("posts", com.travel.web_oasis.domain.entity.Post.class, com.travel.web_oasis.domain.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath provider = createString("provider");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final EnumPath<Status> status = createEnum("status", Status.class);

    public final ListPath<com.travel.web_oasis.domain.entity.Follow, com.travel.web_oasis.domain.entity.QFollow> toFollows = this.<com.travel.web_oasis.domain.entity.Follow, com.travel.web_oasis.domain.entity.QFollow>createList("toFollows", com.travel.web_oasis.domain.entity.Follow.class, com.travel.web_oasis.domain.entity.QFollow.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

