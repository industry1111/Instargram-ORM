package com.travel.web_oasis.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.travel.web_oasis.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

@Getter
@JsonIdentityInfo(generator = IntSequenceGenerator.class, property = "id")
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 3000)
    private String content;


    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FileAttach> fileAttachList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LikeBoard> LikeBoardList = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    public void addFile(FileAttach fileAttach) {

        this.fileAttachList.add(fileAttach);
        fileAttach.setPost(this);
    }

    @Builder
    public Post(Long id, String content, Member member, List<FileAttach> fileAttachList) {
        this.id = id;
        this.content = content;
        setMember(member);
        if (fileAttachList != null) {
            for (FileAttach file : fileAttachList) {
                this.addFile(file);
            }
        }
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void chaneContent(String updateContent) {
        this.content = updateContent;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", filesAttachList=" + fileAttachList +
                ", member=" + member +
                ", comment=" + commentList+
                '}';
    }
}