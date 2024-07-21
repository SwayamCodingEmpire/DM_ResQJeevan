package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LocationRepo extends JpaRepository<Location,String> {
    List<Location> findByChatId(String chatId);
}
