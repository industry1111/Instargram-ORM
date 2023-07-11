package com.travel.web_oasis.domain.member;

import com.travel.web_oasis.domain.entity.*;
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

    private String introduction;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String picture;
    private String provider;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    private List<Follow> fromFollows = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    private List<Follow> toFollows = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LikeBoard> LikeBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String email, String password,Role role,
                  Status status, String provider, String picture,String introduction) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.provider = provider;
        this.picture = picture;
        this.introduction = introduction;
    }

    public void update(MemberDTO memberDto) {
        if (memberDto.getName() != null) {
            this.name = memberDto.getName();
        }
        if (memberDto.getStatus() != null) {
            this.status = memberDto.getStatus();
        }
        if (memberDto.getPicture() != null) {
            this.picture = memberDto.getPicture();
        }
        if (memberDto.getIntroduction() != null) {
            this.introduction = memberDto.getIntroduction();
        }
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
                ", picture='" + picture + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
