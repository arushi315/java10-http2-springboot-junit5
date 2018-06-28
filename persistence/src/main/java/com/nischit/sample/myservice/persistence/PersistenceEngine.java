package com.nischit.sample.myservice.persistence;

import com.nischit.sample.myservice.domain.teams.TeamDetails;

import java.util.Optional;

public interface PersistenceEngine {

    void createTeam(final TeamDetails teamDetails);

    Optional<TeamDetails> getTeamInfo(final String teamId);

    void deleteTeam(final String teamId);
}
