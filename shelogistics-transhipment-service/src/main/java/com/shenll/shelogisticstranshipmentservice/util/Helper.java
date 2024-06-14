package com.shenll.shelogisticstranshipmentservice.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Helper {

//    public static TokenData getTokenDetailsOfLoggenUser(final String token) {
//        Base64.Decoder decoder = Base64.getDecoder();
//        final String[] jwtToken = token.split("\\.");
//        String tokenInfo = new String(decoder.decode(jwtToken[1]), StandardCharsets.UTF_8);
//        return new Gson().fromJson(tokenInfo, TokenData.class);
//    }

    public static TokenData getTokenDetailsFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Base64.Decoder decoder = Base64.getDecoder();
            final String[] jwtToken = token.split("\\.");
            String tokenInfo = new String(decoder.decode(jwtToken[1]), StandardCharsets.UTF_8);
            return new Gson().fromJson(tokenInfo, TokenData.class);
        }
        return null;
    }
}
