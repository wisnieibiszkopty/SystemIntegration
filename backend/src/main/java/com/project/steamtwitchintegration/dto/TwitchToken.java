package com.project.steamtwitchintegration.dto;

import lombok.Data;

@Data
public class TwitchToken {

    private String accessToken;
    private String tokenType;
    private Integer expiressIn;

}
