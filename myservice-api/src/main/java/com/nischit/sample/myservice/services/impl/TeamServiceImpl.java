package com.nischit.sample.myservice.services.impl;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.persistence.PersistenceEngine;
import com.nischit.sample.myservice.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final PersistenceEngine persistenceEngine;

    @Autowired
    public TeamServiceImpl(final PersistenceEngine persistenceEngine) {
        this.persistenceEngine = persistenceEngine;
    }

    @Override
    public void createTeam(TeamDetails teamDetails) {
        this.persistenceEngine.createTeam(teamDetails);
    }

    @Override
    public TeamDetails getTeamInfo(String teamId) {
        Optional<TeamDetails> teamDetails = persistenceEngine.getTeamInfo(teamId);
        return teamDetails.map(teamDet -> teamDet).orElseThrow(() -> new TeamNotFoundException(teamId));
    }

    @Override
    public void deleteTeam(final String teamId) {
        persistenceEngine.deleteTeam(teamId);
    }
}
