package com.travel.web_oasis.config.oauth.provider;

import java.util.Map;

public class GithubUserInfo implements OAuth2UserInfo{
    Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return "github";
    }

    @Override
    public String getProvider() {
        return (String) attributes.get("id");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("login");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getPicture() {
        return (String) attributes.get("avatar_url");
    }
}
