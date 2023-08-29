package com.mpt.goodsservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.mpt.goodsservice.config.UrlConfig;
import com.mpt.goodsservice.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;
    
    @Value("${mpt.server.suffix.access}")
    private String ACCESS;
    
    public UserResponse getAuthResponse(HttpHeaders httpHeaders){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", httpHeaders.getFirst("Authorization"));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        String url = UrlConfig.AUTH_SERVER.getUrl() + ACCESS;

        try {
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            return getUserResponse(result);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return UserResponse.builder().status("Auth Check Failed :").build();
    }

    public UserResponse getUserResponse(ResponseEntity<String> response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody(),UserResponse.class);
    }
}
