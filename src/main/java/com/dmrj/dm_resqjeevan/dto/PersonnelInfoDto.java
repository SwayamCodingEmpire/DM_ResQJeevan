package com.dmrj.dm_resqjeevan.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelInfoDto {
    private String regimentNo;
    private String username;
    private String password;
    private String name;
    private String positionRank;
    private String location;
    private MultipartFile image;
}
