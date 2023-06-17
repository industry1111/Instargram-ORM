package com.travel.web_oasis.domain.member;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.posts.Post;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = true, length = 13)
    private String phone;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

}
