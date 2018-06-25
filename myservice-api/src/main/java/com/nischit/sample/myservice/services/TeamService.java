package com.nischit.sample.myservice.services;

import com.nischit.sample.myservice.domain.teams.TeamDetails;

import java.util.Optional;

public interface TeamService {

    void createTeam(final TeamDetails teamDetails);

    TeamDetails getTeamInfo(final String teamId);

    void deleteTeam(final String teamId);
}
