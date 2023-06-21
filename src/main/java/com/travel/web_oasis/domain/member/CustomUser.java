package com.travel.web_oasis.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Log
public class CustomUser extends User {

    private static final Long serialVersionUID = 1L;

    private final Member member;


    public CustomUser(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getEmail(), member.getPassword(), authorities);
        this.member = member;
        log.info(member.toString());
    }
}
