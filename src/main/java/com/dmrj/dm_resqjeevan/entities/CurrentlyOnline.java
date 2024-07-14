package com.dmrj.dm_resqjeevan.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "currently_online")
@Table(name = "currently_online")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentlyOnline {
    @Id
    private String sessionId;
    @Column(nullable = false)
    private String username;
}
