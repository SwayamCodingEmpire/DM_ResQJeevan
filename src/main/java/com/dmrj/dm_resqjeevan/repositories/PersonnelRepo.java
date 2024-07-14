package com.dmrj.dm_resqjeevan.repositories;

import com.dmrj.dm_resqjeevan.entities.PersonnelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonnelRepo extends JpaRepository<PersonnelInfo,String> {
    PersonnelInfo findByUsername(String username);
    @Modifying
    @Transactional
    @Query("UPDATE personnels_Info p SET p.role=:role WHERE p.regimentNo=:regimentNo")
    int updateRole(@Param("regimentNo") String regimentNo, @Param("role") String role);
    List<PersonnelInfo> findAllByLocation(String location);
    List<PersonnelInfo> findAllByRole(String role);
    @Modifying
    @Transactional
    @Query("UPDATE personnels_Info p SET p.teamId=:teamId WHERE p.regimentNo=:regimentNo")
    int updateTeamIdByRegimentNo(@Param("regimentNo") String regimentNo, @Param("teamId") String teamId);
}
