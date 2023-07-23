package com.travel.web_oasis.web.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.travel.web_oasis.web.dto.QMemberDTO is a Querydsl Projection type for MemberDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberDTO extends ConstructorExpression<MemberDTO> {

    private static final long serialVersionUID = 1935512519L;

    public QMemberDTO(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> picture, com.querydsl.core.types.Expression<String> introduction, com.querydsl.core.types.Expression<Boolean> followStatus) {
        super(MemberDTO.class, new Class<?>[]{long.class, String.class, String.class, String.class, boolean.class}, id, name, picture, introduction, followStatus);
    }

}

