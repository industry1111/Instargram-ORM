package com.travel.web_oasis.config.oauth.dto;

import com.travel.web_oasis.config.oauth.domain.SnsMember;
import com.travel.web_oasis.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes,registrationId);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes, registrationId);
        } else if ("github".equals(registrationId )) {
            return ofGithub(userNameAttributeName, attributes, registrationId);
        }
        return ofGoogle(userNameAttributeName, attributes,registrationId);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes,String registrationId) {
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .provider(registrationId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGithub(String userNameAttributename, Map<String, Object> attributes, String registrationId) {
        System.out.println(attributes.toString());
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .provider(registrationId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributename)
                .build();
    }
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes, String registrationId) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println("response = " + response);
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .provider(registrationId)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String registrationId) {
        Map<String,Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String, Object>) account.get("profile");
        System.out.println("=====profile=====" + profile.get("image_url"));

        System.out.println(account.toString());

        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .picture((String) profile.get("image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(registrationId)
                .build();
    }

    public SnsMember toEntity() {
        return SnsMember.builder()
                .name(this.name)
                .email(this.email)
                .picture(this.picture)
                .role(Role.USER)
                .provider(this.provider)
                .build();
    }
}
