package com.nnk.springboot.utils;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.security.Principal;
import java.util.Map;


public class AuthUtils {
    public  static  String getOAuth2User(Principal principal) {
        if(principal instanceof OAuth2AuthenticationToken) {
            Map<String , Object> detail = (Map<String ,Object>) ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            return (String) detail.get("login");
        } else {
            return null;
        }
    }
}
