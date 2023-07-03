package com.travel.web_oasis.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
    Map<String,Object> kakaoAccount;
    Map<String,Object> profile;


    public KakaoUserInfo(Map<String,Object> kakaoAccount,Map<String,Object> profile) {
        this.kakaoAccount = kakaoAccount;
        this.profile = profile;
    }
    @Override
    public String getProviderId() {
        return "kakao";
    }

    @Override
    public String getProvider() {
        return (String) kakaoAccount.get("id");
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getName() {
        return (String) profile.get("nickname");
    }

    @Override
    public String getPicture() {
        return  (String) profile.get("image");
    }
}
