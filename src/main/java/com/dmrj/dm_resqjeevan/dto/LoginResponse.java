package com.dmrj.dm_resqjeevan.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String token;
    private long expiresIn;;
}
