package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.ResourceDepots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDepotsRepo extends JpaRepository<ResourceDepots, String> {
}
