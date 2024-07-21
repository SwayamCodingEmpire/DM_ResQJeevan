package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.ResourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceInfoRepo extends JpaRepository<ResourceInfo, String> {
}
