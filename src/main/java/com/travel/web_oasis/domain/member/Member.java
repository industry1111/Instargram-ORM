package com.travel.web_oasis.domain.member;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.MemberDTO;
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
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String picture;
    private Boolean is_Auth;
    private String provider;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password,Role role,
                  Status status, String provider, Boolean is_Auth,String picture,String bio) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.provider = provider;
        this.is_Auth = is_Auth;
        this.picture = picture;
        this.bio = bio;
    }

    public void update(MemberDTO memberDto) {
        this.name = memberDto.getName();
        this.status = memberDto.getStatus();
        this.picture = memberDto.getPicture();
        this.bio = memberDto.getBio();
    }




    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='"+password+'\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", provider='" + provider + '\'' +
                ", is_Auth='" + is_Auth + '\'' +
                ", picture='" + picture + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
