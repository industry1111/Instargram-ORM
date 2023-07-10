package com.travel.web_oasis.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileAttach is a Querydsl query type for FileAttach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileAttach extends EntityPathBase<FileAttach> {

    private static final long serialVersionUID = -267433097L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileAttach fileAttach = new QFileAttach("fileAttach");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath fileStoreName = createString("fileStoreName");

    public final StringPath fileType = createString("fileType");

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final QPost post;

    public QFileAttach(String variable) {
        this(FileAttach.class, forVariable(variable), INITS);
    }

    public QFileAttach(Path<? extends FileAttach> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileAttach(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileAttach(PathMetadata metadata, PathInits inits) {
        this(FileAttach.class, metadata, inits);
    }

    public QFileAttach(Class<? extends FileAttach> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

