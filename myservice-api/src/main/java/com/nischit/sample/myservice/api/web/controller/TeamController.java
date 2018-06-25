package com.nischit.sample.myservice.api.web.controller;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nischit.sample.myservice.api.support.ApiUrls.TEAMS_API_URI;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(TEAMS_API_URI)
public class TeamController {

    private static final String TEAM_REQUEST_API_VERSION = "application/vnd.nischit.myservice.teams-add-v1+json";
    private static final String TEAM_RESPONSE_API_VERSION = "application/vnd.nischit.myservice.teams-response-v1+json";

    private static final String TEAM_ID = "teamId";
    private static final String TEAM_ID_URI = "/{" + TEAM_ID + "}";

    private final TeamService teamService;

    @Autowired
    public TeamController(final TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(consumes = TEAM_REQUEST_API_VERSION)
    public ResponseEntity<Void> createTeam(final TeamDetails teamDetails) {
        teamService.createTeam(teamDetails);
        return new ResponseEntity<Void>(NO_CONTENT);
    }

    @GetMapping(value = TEAM_ID_URI, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<TeamDetails> getTeamInfo(@PathVariable(TEAM_ID) final String teamId) {
        return new ResponseEntity<>(teamService.getTeamInfo(teamId), OK);
    }

    @DeleteMapping(value = TEAM_ID_URI, produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> deleteTeam(@PathVariable(TEAM_ID) final String teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<Void>(NO_CONTENT);
    }
}
