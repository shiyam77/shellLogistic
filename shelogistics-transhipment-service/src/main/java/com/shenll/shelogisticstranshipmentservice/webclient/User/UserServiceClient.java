package com.shenll.shelogisticstranshipmentservice.webclient.User;

import com.shenll.shelogisticstranshipmentservice.webclient.Customer.CustomerObjectResponse;
import com.shenll.shelogisticstranshipmentservice.webclient.Customer.CustomerVehDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceClient {
    @Autowired
    WebClient webClient;

    @Autowired
    HttpServletRequest request;
    public Optional<UserVehDTO> getUserById(String agentId) {
        System.out.println("hello");
        String token = request.getHeader("Authorization");
        String s = token.substring(7);
        String agentServiceUrl = UriComponentsBuilder.fromUriString("http://localhost:8082")
                .path("/shelogistics/get/users/{id}")
                .buildAndExpand(agentId)
                .toUriString();
        System.out.println("hello2>>" + agentServiceUrl);
        return Objects.requireNonNull(webClient.get()
                        .uri(agentServiceUrl)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
                        .retrieve()
                        .bodyToMono(UserObjectResponse.class)
                        .block())
                .getData();
    }
}
