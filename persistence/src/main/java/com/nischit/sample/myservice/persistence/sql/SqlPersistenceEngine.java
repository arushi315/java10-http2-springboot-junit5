package com.nischit.sample.myservice.persistence.sql;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.persistence.PersistenceEngine;
import com.nischit.sample.myservice.persistence.sql.entity.TeamDetailsEntity;
import com.nischit.sample.myservice.persistence.sql.repository.TeamDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SqlPersistenceEngine implements PersistenceEngine {

    private final TeamDetailsRepository teamDetailsRepository;

    @Autowired
    public SqlPersistenceEngine(final TeamDetailsRepository teamDetailsRepository) {
        this.teamDetailsRepository = teamDetailsRepository;
    }

    @Override
    public void createTeam(TeamDetails teamDetails) {
        final TeamDetailsEntity teamDetailsEntity = new TeamDetailsEntity.Builder()
                .teamId(teamDetails.getTeamId())
                .teamName(teamDetails.getTeamName())
                .teamDesc(teamDetails.getTeamDesc())
                .build();
        teamDetailsRepository.save(teamDetailsEntity);
    }

    @Override
    public Optional<TeamDetails> getTeamInfo(final String teamId) {
        final Optional<TeamDetailsEntity> teamEntity = teamDetailsRepository.findById(teamId);
        return teamEntity.map(teamDetailsEntity -> Optional.of(new TeamDetails.Builder()
                    .teamId(teamDetailsEntity.getTeamId())
                    .teamName(teamDetailsEntity.getTeamName())
                    .teamDesc(teamDetailsEntity.getTeamDesc())
                    .build()))
                .orElse(Optional.empty());
    }

    @Override
    public void deleteTeam(final String teamId) {
        teamDetailsRepository.deleteById(teamId);
    }
}
