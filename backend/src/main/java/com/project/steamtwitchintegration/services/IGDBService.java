package com.project.steamtwitchintegration.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.steamtwitchintegration.dto.TwitchToken;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.repositories.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

// TODO Get list of all games and load corresponding data to them

@Slf4j
@Service
public class IGDBService {
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private GameRepository gameRepository;

    @Value("${api.client_id}")
    private String clientId;
    @Value("${api.client_secret}")
    private String clientSecret;
    private final String grantType = "client_credentials";
    private final String baseUrl = "https://api.igdb.com/v4/";
    private final String twitchUrl = "https://id.twitch.tv/oauth2/token";
    private TwitchToken token;

    public IGDBService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void loadTwitchToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(twitchUrl)
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("grant_type", grantType);

        String finalUrl = builder.toUriString();

        try{
            token = restTemplate
                .exchange(finalUrl, HttpMethod.POST, request, TwitchToken.class)
                .getBody();
            System.out.println(token);
        } catch (HttpClientErrorException e){
            log.error("Http client exception: " + e);
        }
    }

    public void sendRequest(String url, String body){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + token.getAccessToken());
        HttpEntity<Object> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate
                .exchange(baseUrl + url, HttpMethod.POST, request, String.class)
                .getBody();
            System.out.println(response);
        } catch(HttpClientErrorException e){
            log.error("Http client exception: " + e);
        }
    }

    public void loadGamesInfo(){
        // TODO Load basic info about game for every unique game in database
        List<Game> games = gameRepository.findAll();

        // load details about every game from api
        games.forEach(game -> {

        });
    }
}
