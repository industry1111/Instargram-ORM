package com.travel.web_oasis.domain.entity;

import com.travel.web_oasis.domain.entity.BaseEntity;
import com.travel.web_oasis.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Follow extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "follw_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    @Builder
    public Follow(Member fromMember, Member toMember) {
        this.fromMember = fromMember;
        this.toMember = toMember;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", fromMember=" + fromMember +
                ", toMember=" + toMember +
                '}';
    }
}
