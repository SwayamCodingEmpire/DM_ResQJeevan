package com.dmrj.dm_resqjeevan.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity(name = "resource_info")
@Table(name = "resource_info")
public class ResourceInfo {
    @Id
    private String ResourceName;
    private int unitsAvailable;
    @ManyToOne
    @JoinColumn(name = "resourceDepot_username")
    private ResourceDepots resourceDepot;
}
