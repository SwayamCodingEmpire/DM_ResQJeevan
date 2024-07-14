package com.dmrj.dm_resqjeevan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoDto {
    private String adminName;
    private String username;
    private String password;
    private String location;
    private String role;
}
