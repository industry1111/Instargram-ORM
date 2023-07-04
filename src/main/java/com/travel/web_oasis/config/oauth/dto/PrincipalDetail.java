package com.travel.web_oasis.config.oauth.dto;

import com.travel.web_oasis.domain.member.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetail extends User implements OAuth2User {

    private Member member;
    private Map<String,Object> attributes;

    public PrincipalDetail(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
    }

    public PrincipalDetail(Member member, Collection<? extends GrantedAuthority> authorities, Map<String,Object> attributes) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }


}
