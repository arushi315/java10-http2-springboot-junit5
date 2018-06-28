package com.nischit.sample.myservice.services.impl;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.persistence.PersistenceEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {

    @Mock
    private PersistenceEngine mockPersistenceEngine;

    @InjectMocks
    private TeamServiceImpl teamService;

    @BeforeEach
    public void setup() {

    }

    @Test
    @DisplayName("When team creation succeeds")
    public void createTeamSucceeds() {
        final TeamDetails teamDetails = new TeamDetails.Builder().teamDesc("some team").teamId("US123").teamName("Phenoix").build();
        teamService.createTeam(teamDetails);
        verify(mockPersistenceEngine).createTeam(any(TeamDetails.class));
    }

    @Test
    @DisplayName("When team deletion succeeds")
    public void deleteTeamSucceeds() {
        final TeamDetails teamDetails = new TeamDetails.Builder().teamDesc("some team").teamId("US123").teamName("Phenoix").build();
        teamService.deleteTeam("US123");
        verify(mockPersistenceEngine).deleteTeam(eq("US123"));
    }
}