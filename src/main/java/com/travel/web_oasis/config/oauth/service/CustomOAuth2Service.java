package com.travel.web_oasis.config.oauth.service;

import com.travel.web_oasis.config.oauth.domain.SnsMember;
import com.travel.web_oasis.config.oauth.dto.OAuthAttributes;
import com.travel.web_oasis.config.oauth.dto.SessionSnsMember;
import com.travel.web_oasis.config.oauth.repository.SnsMemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final SnsMemberRepository snsMemberRepository;
    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        SnsMember snsMember = saveOrUpdate(attributes);

        httpSession.setAttribute("snsMember", new SessionSnsMember(snsMember));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + snsMember.getRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }


    private SnsMember saveOrUpdate(OAuthAttributes attributes){
        SnsMember snsMember = snsMemberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture(),attributes.getProvider()))
                .orElse(attributes.toEntity());
        return snsMemberRepository.save(snsMember);
    }
}
