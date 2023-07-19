package com.travel.web_oasis.config.oauth.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.config.oauth.provider.*;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if (registrationId.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
            System.out.println("oAuth2UserInfo = " + oAuth2UserInfo);
        } else if (registrationId.equals("naver")) {
            Map<String,Object> attributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oAuth2UserInfo = new NaverUserInfo(attributes);
        } else if (registrationId.equals("github")) {
            Map<String,Object> attributes =  oAuth2User.getAttributes();
            oAuth2UserInfo = new GithubUserInfo(attributes);
        } else if (registrationId.equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            oAuth2UserInfo = new KakaoUserInfo(kakaoAccount,profile);
        } else {
        }



        String email = oAuth2UserInfo.getEmail();
        String username = oAuth2UserInfo.getName();
        String picture = oAuth2UserInfo.getPicture();
        if (picture == null || picture == "") {
            picture = "profileImg.png";
        }
        String password = passwordEncoder.encode("123123");
        Enum role = Role.USER;

        Member member = memberRepository.findByEmailAndProvider(email,registrationId);
        if (member == null) {
            member = Member.builder()
                    .email(email)
                    .name(username)
                    .introduction("")
                    .password(password)
                    .provider(registrationId)
                    .role((Role) role)
                    .picture(picture)
                    .status(Status.PUBLIC)
                    .build();
            memberRepository.save(member);
        }

        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));

            return new PrincipalDetail(member,roles, oAuth2User.getAttributes());
        }

}
