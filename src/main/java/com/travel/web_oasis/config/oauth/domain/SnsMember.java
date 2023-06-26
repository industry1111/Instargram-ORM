package com.travel.web_oasis.config.oauth.domain;

import com.travel.web_oasis.domain.member.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SnsMember {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;

    private String picture;

    private String provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public SnsMember(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public SnsMember update(String name, String picture,String provider) {
        this.name = name;
        this.picture = picture;
        this.provider = provider;
        return this;
    }


}
