package com.travel.web_oasis.domain.entity;

import com.travel.web_oasis.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class LikeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    Post post;

    @Builder
    public LikeBoard(Long id, Member member, Post post) {
        this.id = id;
        this.member= member;
        this.post = post;
    }


}
