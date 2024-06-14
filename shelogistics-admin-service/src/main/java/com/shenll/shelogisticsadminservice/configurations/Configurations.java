package com.shenll.shelogisticsadminservice.configurations;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.urls.auth}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.admin.secret}")
    private String adminSecret;

    @Value("${keycloak.admin.clientid}")
    private String adminClientId;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(adminClientId)
                .clientSecret(adminSecret)
                .build();
    }
    @Bean
    public Keycloak keycloakRealmRole() {
        return  KeycloakBuilder.builder()
                .serverUrl(authUrl)
                .realm(realm)
                .clientId(adminClientId)
                .clientSecret(clientSecret)
                .username("admin")
                .password("admin")
                .build();
    }


//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        return mapper;
//    }

}

