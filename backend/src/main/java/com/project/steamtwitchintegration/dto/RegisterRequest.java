package com.project.steamtwitchintegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    //private String firstname;
    //private String lastname;
    private String fullname;
    private String email;
    private String password;

}
