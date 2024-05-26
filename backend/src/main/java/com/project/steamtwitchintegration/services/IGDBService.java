package com.project.steamtwitchintegration.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.steamtwitchintegration.dto.TwitchToken;
import com.project.steamtwitchintegration.models.*;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.repositories.PlayerPerspectiveRepository;
import jakarta.persistence.EntityManager;
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
import java.util.stream.Collectors;

// TODO Get list of all games and load corresponding data to them

@Slf4j
@Service
public class IGDBService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EntityManager em;
    private ObjectMapper objectMapper;
    private GameRepository gameRepository;
    private PlayerPerspectiveRepository perspectiveRepository;

    @Value("${api.client_id}")
    private String clientId;
    @Value("${api.client_secret}")
    private String clientSecret;
    private final String grantType = "client_credentials";
    private final String baseUrl = "https://api.igdb.com/v4/";
    private final String twitchUrl = "https://id.twitch.tv/oauth2/token";
    private TwitchToken token;

    public IGDBService(
            ObjectMapper objectMapper,
            GameRepository gameRepository,
            PlayerPerspectiveRepository perspectiveRepository) {
        this.objectMapper = objectMapper;
        this.gameRepository = gameRepository;
        this.perspectiveRepository = perspectiveRepository;
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

    /**
     * https://api-docs.igdb.com/#endpoints
     * Valid endpoint can be chosen from this website
     * Valid body is also specified in api docs
     * General info can be obtained with "fields *;"
     */
    public JsonNode sendRequest(String endpoint, String body){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + token.getAccessToken());
        HttpEntity<Object> request = new HttpEntity<>(body, headers);

        try {
            JsonNode response = restTemplate
                .exchange(baseUrl + endpoint, HttpMethod.POST, request, JsonNode.class)
                .getBody();
            System.out.println(response);
            return response;
        } catch(HttpClientErrorException e){
            log.error("Http client exception: " + e);
        }

        // 🤡🤡🤡
        return null;
    }

    // over engineering
//    public void loadGameGeneralInfo(String endpoint, Loadable object){
//        log.info("Loading " + endpoint);
//        JsonNode nodes = sendRequest(endpoint, "fields name;");
//        if(nodes != null && nodes.isArray()){
//            for(JsonNode node: nodes){
////                object.setId(node.get("id").asLong());
////                object.setName(node.get("name").asText());
//                em.persist(object);
//            }
//        }
//        log.info("Finished loading " + endpoint);
//    }

    // TODO refactor
    public void loadGameGeneralInfo(){
//        log.info("Loading game modes info...");
//
//        JsonNode gameModesResponse = sendRequest("game_modes","fields name;");
//        if(gameModesResponse != null && gameModesResponse.isArray()){
//            for(JsonNode gameMode: gameModesResponse){
//                GameMode mode = new GameMode(
//                     gameMode.get("id").asLong(),
//                     gameMode.get("name").asText()
//                );
//                em.persist(mode);
//            }
//        }
//
//        log.info("Finished loading game modes info");

//        // not sure if this endpoint provides every genre
//        log.info("Loading game genres...");
//        JsonNode gameGenres = sendRequest("genres", "fields name;");
//        if(gameGenres != null && gameGenres.isArray()){
//            for(JsonNode gameGenre: gameGenres){
//                GameGenre genre = new GameGenre(
//                        gameGenre.get("id").asLong(),
//                        gameGenre.get("name").asText()
//                );
//                em.persist(genre);
//            }
//        }
        log.info("Finished loading game genres");

        log.info("Loading player perspectives...");
        JsonNode playerPerspectives = sendRequest("player_perspectives","fields name;");
        if(playerPerspectives != null && playerPerspectives.isArray()){
            for(JsonNode playerPerspective: playerPerspectives){
                PlayerPerspective perspective = new PlayerPerspective(
                    playerPerspective.get("id").asLong(),
                    playerPerspective.get("name").asText()
                );
                em.persist(perspective);
            }
        }
        log.info("Finished loading player perspectives");
    }

    // maybe can be done with one request
    public void loadGamesInfo(){
        // TODO Load basic info about game for every unique game in database
        List<Game> games = gameRepository.findAll().subList(1, 2);
        System.out.println("count: " + games.size());
        String bodyGeneral = "fields cover, game_modes, genres, player_perspectives, rating, rating_count, total_rating, total_rating_count; where name = \"";

        // load details about every game from api
        int i = 0;
        for(Game game : games){
            String body =
                bodyGeneral
                + game.getGameName() + "\";";
            System.out.println(body);

            JsonNode jsonResponse = sendRequest("games", body);
            // returns array with one element
            for(JsonNode gameInfo: jsonResponse){
                // setting basic info
                game.setRating(gameInfo.get("rating").asDouble());
                game.setRatingCount(gameInfo.get("rating_count").asInt());
                game.setTotalRating(gameInfo.get("total_rating").asDouble());
                game.setTotalRatingCount(gameInfo.get("total_rating_count").asInt());

                // load url based on received cover id
                JsonNode coverResponse = sendRequest("covers", "fields url; where id = " + gameInfo.get("cover") + ";");
                for(JsonNode coverInfo: coverResponse){
                    String coverUrl = coverInfo.get("url").asText().replace("t_thumb", "t_cover_big");
                    game.setCoverUrl(coverUrl);
                }

                // setting many-to-many relationships
                // don't work yet
//                JsonNode perspectiveNode = gameInfo.get("player_perspectives");
//                List<Long> perspectivesList = objectMapper.convertValue(perspectiveNode, new TypeReference<List<Long>>() {});
//                log.info(perspectivesList.toString());
//                List<PlayerPerspective> perspectives = perspectiveRepository.findAllById(perspectivesList).stream().toList();
//                //game.addPerspective(perspectives);
//                perspectives.forEach(p -> {
//                    game.addPerspective(p);
//                });
            }
        }

        gameRepository.saveAll(games);
    }
}
