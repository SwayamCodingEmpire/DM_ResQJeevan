package com.dmrj.dm_resqjeevan.services.serviceImpl;

import com.dmrj.dm_resqjeevan.dto.TeamDto;
import com.dmrj.dm_resqjeevan.helpers.AppConstants;
import com.dmrj.dm_resqjeevan.repositories.PersonnelRepo;
import com.dmrj.dm_resqjeevan.services.LeaderService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LeaderServiceImpl implements LeaderService {
    private final PersonnelRepo personnelRepo;

    public LeaderServiceImpl(PersonnelRepo personnelRepo) {
        this.personnelRepo = personnelRepo;
    }

    @Override
    public Boolean formTeam(TeamDto team) {
        for(String memberRegNo : team.getMembers()){
            if(personnelRepo.existsById(memberRegNo)){
                personnelRepo.updateTeamIdByRegimentNo(memberRegNo,team.getTeamLeader());
            }
            else{
                throw new UsernameNotFoundException("No such member exists");
            }
        }
        if(personnelRepo.existsById(team.getTeamLeader())){
            personnelRepo.updateTeamIdByRegimentNo(team.getTeamLeader(),team.getTeamLeader());
            personnelRepo.updateRole(team.getTeamLeader(), AppConstants.ROLE_LEADER);
            return true;
        }
        else {
            throw new UsernameNotFoundException("No such leader exists");
        }
    }
}
