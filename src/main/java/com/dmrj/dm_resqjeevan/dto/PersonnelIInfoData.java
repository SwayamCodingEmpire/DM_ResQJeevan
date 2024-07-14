package com.dmrj.dm_resqjeevan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonnelIInfoData {
    private String regimentNo;
    private String username;
    private String password;
    private String name;
    private String positionRank;
    private String location;
}
