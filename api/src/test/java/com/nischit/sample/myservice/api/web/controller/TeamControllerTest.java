package com.nischit.sample.myservice.api.web.controller;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.services.TeamService;
import com.nischit.sample.myservice.services.impl.TeamNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @Mock
    private TeamService mockTeamService;

    @InjectMocks
    private TeamController teamController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("When team creation is successful")
    public void createTeamSucceeds() {
        final TeamDetails teamDetails = new TeamDetails.Builder().teamDesc("some team").teamId("US123").teamName("Phenoix").build();
        final ResponseEntity<Void> voidResponseEntity = teamController.createTeam(teamDetails);
        assertEquals(NO_CONTENT, voidResponseEntity.getStatusCode());
        verify(mockTeamService).createTeam(any(TeamDetails.class));
    }

    @Test
    @DisplayName("When team deletion is successful")
    public void deleteTeamSucceeds() {
        final ResponseEntity<Void> voidResponseEntity = teamController.deleteTeam("US123");
        assertEquals(NO_CONTENT, voidResponseEntity.getStatusCode());
        verify(mockTeamService).deleteTeam(eq("US123"));
    }

    @Test
    @DisplayName("When team retrieval is successful")
    public void teamRetrievalSucceeds() {
        when(mockTeamService.getTeamInfo(eq("US123"))).thenReturn(new TeamDetails.Builder().teamDesc("some team").teamId("US123").teamName("Phenoix").build());
        final ResponseEntity<TeamDetails> teamDetailsResponseEntity = teamController.getTeamInfo("US123");
        assertAll(() -> {
            assertEquals(OK, teamDetailsResponseEntity.getStatusCode());
            assertEquals("US123", teamDetailsResponseEntity.getBody().getTeamId());
        });
    }

    @Test
    @DisplayName("When team is not found")
    public void teamIsNotFound() {
        when(mockTeamService.getTeamInfo(eq("US123"))).thenThrow(new TeamNotFoundException());
        final TeamNotFoundException acutalExcp = assertThrows(TeamNotFoundException.class, () -> teamController.getTeamInfo("US123"));
        assertNotNull(acutalExcp);
    }
}