package com.shenll.shelogisticsapigateway.admin;

import com.shenll.shelogisticsapigateway.exception.AppException;
import com.shenll.shelogisticsapigateway.requestDTO.UserDTO;
import com.shenll.shelogisticsapigateway.responseDTO.CommonResponse;
import com.shenll.shelogisticsapigateway.responseDTO.DesignationDTO;
import com.shenll.shelogisticsapigateway.utils.TokenExtractor;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    WebClient webClient;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/create/update/users")
    public CommonResponse createOrUpdateUser(UserDTO userDTO) {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            CommonResponse cmd = webClient.post()
                    .uri("http://localhost:8082/shelogistics/user/create/update/user")
                    .body(BodyInserters.fromValue(userDTO))
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();

            return CommonResponse.builder().message("successfully saved").data(cmd.getData()).build();
        } catch (Exception e) {
            return CommonResponse.builder().message("server error occurs").code(500).build();
        }
    }


    @GetMapping("/get/users")
    public CommonResponse findAllUsers(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) String phone,
                                       @RequestParam(required = false) String id) {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            String uri = UriComponentsBuilder.fromUriString("http://localhost:8082/shelogistics/get/user/filters")
                    .queryParam("name", name)
                    .queryParam("email", email)
                    .queryParam("phone", phone)
                    .queryParam("id", id)
                    .build()
                    .toUriString();
            CommonResponse responseEntity = webClient.get()
                    .uri(uri)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();

            return CommonResponse.builder().data(responseEntity).build();
        } catch (Exception e) {
            throw new AppException("Exception occurs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/get/designation")
    public CommonResponse getAllDesignation() {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            CommonResponse responseEntity = webClient.get()
                    .uri("http://localhost:8082/shelogistics/get/designations")
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();

            return CommonResponse.builder().data(responseEntity).build();
        } catch (Exception e) {
            throw new AppException("Exception occurs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create/update/designation")
    public CommonResponse createAndUpdateDesignation(@RequestBody DesignationDTO designationDTO) {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            CommonResponse responseEntity = webClient.post()
                    .uri("http://localhost:8082/shelogistics/create/designations")
                    // .body(BodyInserters.fromValue(designationDTO))
                    .bodyValue(designationDTO)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();
            return CommonResponse.builder().data(responseEntity).build();
        } catch (Exception e) {
            throw new AppException("Exception occurs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/agent")
    public CommonResponse getAllAgent(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String code,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String phone,
                                      @RequestParam(required = false) Long page,
                                      @RequestParam(required = false) Long size) {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            String uri = UriComponentsBuilder.fromUriString("http://localhost:8082/shelogistics/get/agent/filter")
                    .queryParam("name", name)
                    .queryParam("code", code)
                    .queryParam("city", city)
                    .queryParam("phone", phone)
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .build()
                    .toUriString();

            CommonResponse responseEntity = webClient.get()
                    .uri(uri)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();

            return CommonResponse.builder().data(responseEntity).build();
        } catch (Exception e) {
            throw new AppException("Exception occurs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/agents")
    public CommonResponse deleteDesignation(@RequestParam String id) {
        try {
            String token = TokenExtractor.extractTokenFromRequest(request);
            URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/shelogistics/delete/agent")
                    .queryParam("id", id)
                    .build().toUri();

            CommonResponse responseEntity = webClient.delete()
                    .uri(uri)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(CommonResponse.class)
                    .block();

            return CommonResponse.builder()
                    .code(responseEntity.getCode())
                    .data(responseEntity.getData())
                    .message(responseEntity.getMessage())
                    .build();
        } catch (Exception e) {
            throw new AppException("Exception occurs: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
