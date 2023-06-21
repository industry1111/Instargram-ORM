package com.travel.web_oasis.domain.member;

import com.travel.web_oasis.domain.BaseEntity;
import com.travel.web_oasis.domain.posts.Post;
import com.travel.web_oasis.web.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(nullable = true, length = 13)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, String phone,Role role, Status status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public void update(MemberDTO memberDto) {
        this.name = memberDto.getName();
        this.password = memberDto.getPassword();
        this.phone = memberDto.getPhone();
        this.status = memberDto.getStatus();
    }

    public static Member register(MemberDTO memberDTO, PasswordEncoder passwordEncoder) {
       return Member.builder()
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .phone(memberDTO.getPhone())
                .role(Role.USER)
                .status(Status.PUBLIC)
                .build();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='"+password+'\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
