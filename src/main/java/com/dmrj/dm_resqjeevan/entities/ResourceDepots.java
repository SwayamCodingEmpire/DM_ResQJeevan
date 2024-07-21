package com.dmrj.dm_resqjeevan.entities;

import com.dmrj.dm_resqjeevan.helpers.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity(name = "resource_depot")
@Table(name = "resource_depot")
public class ResourceDepots implements UserDetails {
    @Id
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "location", nullable = false)
    private String location;
    private double lat;
    private double lng;
    private String role;
    private Status status;
    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;
    @OneToMany(mappedBy = "resourceDepots",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResourceInfo> resourceInfos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
