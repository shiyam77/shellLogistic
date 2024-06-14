package com.shenll.shelogisticsadminservice.configurations.keycloakConfigurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.jwt.uri}")
    private String jwturi;


    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        DelegatingJwtGrantedAuthoritiesConverter authoritiesConverter = new DelegatingJwtGrantedAuthoritiesConverter(
                new JwtGrantedAuthoritiesConverter(),
                new KeycloakJwtRolesConverter(realm));
        http
                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/shelogistics/getAllAgent").hasAnyRole("Admin", "Transhipment Admin")
                        .requestMatchers("/shelogistics/getAgent/**").hasAnyRole("Admin", "Transhipment Admin")
                        .requestMatchers("/shelogistics/getCustomer/**").hasAnyRole("Admin", "Transhipment Admin")
                        .requestMatchers("/shelogistics/get/users/**").hasAnyRole("Admin", "Transhipment Admin")
                        .requestMatchers("/**").hasRole("Admin")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(innerJwt -> {
                    innerJwt.jwtAuthenticationConverter(
                            jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt))
                    );
                }));
        return http.build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = jwturi;
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
                .build();
    }
//    private boolean isExpired(OAuth2AccessToken accessToken) {
//        Instant now = Instant.now();
//        Instant expiresAt = accessToken.getExpiresAt();
//        return now.isAfter(expiresAt.minus(Duration.ofMinutes(1L)));
//    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }

}