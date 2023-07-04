package com.travel.web_oasis.config.oauth.service;

import com.travel.web_oasis.config.oauth.dto.PrincipalDetail;
import com.travel.web_oasis.config.oauth.provider.*;
import com.travel.web_oasis.domain.member.Member;
import com.travel.web_oasis.domain.member.Role;
import com.travel.web_oasis.domain.member.Status;
import com.travel.web_oasis.domain.repository.MemberRepository;
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
        System.out.println("registrationId = " + registrationId);

        OAuth2UserInfo oAuth2UserInfo = null;

        if (registrationId.equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            System.out.println("네이버 로그인 요청");
            Map<String,Object> attributes = (Map<String, Object>) oAuth2User.getAttributes().get("response");
            oAuth2UserInfo = new NaverUserInfo(attributes);
        } else if (registrationId.equals("github")) {
            System.out.println("깃허브 로그인 요청");
            Map<String,Object> attributes =  oAuth2User.getAttributes();
            oAuth2UserInfo = new GithubUserInfo(attributes);
            for (String s : attributes.keySet()) {
                System.out.println(s + " : " + attributes.get(s));
            }
        } else if (registrationId.equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            oAuth2UserInfo = new KakaoUserInfo(kakaoAccount,profile);
        } else {
            System.out.println("우리는 구글과 네이버만 지원해요 ㅎㅎ");
        }


        String email = oAuth2UserInfo.getEmail();
        String username = oAuth2UserInfo.getName();
        String picture = oAuth2UserInfo.getPicture();
        String password = passwordEncoder.encode("123123");
        Enum role = Role.USER;

        Member member = memberRepository.findByEmailAndProvider(email,registrationId);
        if (member == null) {
            member = Member.builder()
                    .email(email)
                    .name(username)
                    .password(password)
                    .provider(registrationId)
                    .role((Role) role)
                    .is_Auth(true)
                    .picture(picture)
                    .status(Status.PUBLIC)
                    .build();
            memberRepository.save(member);
            System.out.println("member = " + member);
            System.out.println("member.getPicture() = " + member.getPicture());
        }
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_"+member.getRole()));

            return new PrincipalDetail(member,roles, oAuth2User.getAttributes());
        }

}
