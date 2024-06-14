package com.shenll.shelogisticstranshipmentservice.webclient.Agent;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgentServiceClient {

    @Autowired
    WebClient webClient;

    @Autowired
    HttpServletRequest request;

    public List<AgentVehDTO> getAgent() {
        String token = request.getHeader("Authorization");
        String s = token.substring(7);
        String agentServiceUrl = UriComponentsBuilder.fromUriString("http://localhost:8082")
                .path("/shelogistics/getAllAgent")
                .toUriString();

        return webClient.get()
                .uri(agentServiceUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
                .retrieve()
                .bodyToMono(AgentListResponse.class)
                .block()
                .getData();
    }

    public Optional<AgentVehDTO> getAgentById(String agentId) {
        System.out.println("hello");
        String token = request.getHeader("Authorization");
        String s = token.substring(7);
        String agentServiceUrl = UriComponentsBuilder.fromUriString("http://localhost:8082")
                .path("/shelogistics/getAgent/{id}")
                .buildAndExpand(agentId)
                .toUriString();
        System.out.println("hello2>>>" + agentServiceUrl);

        return Objects.requireNonNull(webClient.get()
                        .uri(agentServiceUrl)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + s)
                        .retrieve()
                        .bodyToMono(AgentObjectResponse.class)
                        .block())
                .getData();
    }
}

