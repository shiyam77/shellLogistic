package com.shenll.shelogisticsapigateway.auth;

import com.shenll.shelogisticsapigateway.responseDTO.CommonResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/auth/login")
public class AuthController {

    @Value("${keycloak.urls.auth}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .username(loginRequest.getUserName())
                    .password(loginRequest.getPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();
            AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();

            String accessToken = accessTokenResponse.getToken();

            AccessToken token = TokenVerifier.create(accessToken, AccessToken.class)
                    .getToken();

            Set<String> rolesSet = token.getRealmAccess().getRoles();
            List<String> roles = new ArrayList<>(rolesSet);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", accessToken);
            responseData.put("roles", roles);
            return CommonResponse.builder()
                    .message("Login successful")
                    .data(responseData)
                    .code(200)
                    .build();
        } catch (Exception e) {
            return CommonResponse.builder()
                    .message("Login failed: " + e.getMessage())
                    .code(401)
                    .build();
        }
    }

}
