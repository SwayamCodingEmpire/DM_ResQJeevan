package com.dmrj.dm_resqjeevan.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private List<String> members = new ArrayList<>();
    private String teamLeader;
}
