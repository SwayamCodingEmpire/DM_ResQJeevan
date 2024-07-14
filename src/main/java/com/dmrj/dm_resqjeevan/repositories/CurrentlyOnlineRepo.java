package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.CurrentlyOnline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentlyOnlineRepo extends JpaRepository<CurrentlyOnline, String> {
    CurrentlyOnline findCurrentlyOnlineByUsername(String username);
}
