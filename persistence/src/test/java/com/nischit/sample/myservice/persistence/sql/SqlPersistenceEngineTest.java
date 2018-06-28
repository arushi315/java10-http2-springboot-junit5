package com.nischit.sample.myservice.persistence.sql;

import com.nischit.sample.myservice.domain.teams.TeamDetails;
import com.nischit.sample.myservice.persistence.sql.entity.TeamDetailsEntity;
import com.nischit.sample.myservice.persistence.sql.repository.TeamDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SqlPersistenceEngineTest {

    @Mock
    private TeamDetailsRepository mockTeamDetailsRepository;

    @InjectMocks
    private SqlPersistenceEngine sqlPersistenceEngine;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("When team creation succeeds")
    public void createTeamSucceeds() {
        final TeamDetails teamDetails = new TeamDetails.Builder().teamDesc("some team").teamId("US123").teamName("Phenoix").build();
        ArgumentCaptor<TeamDetailsEntity> teamDetailsArgumentCaptor = ArgumentCaptor.forClass(TeamDetailsEntity.class);
        sqlPersistenceEngine.createTeam(teamDetails);
        verify(mockTeamDetailsRepository).save(teamDetailsArgumentCaptor.capture());
        assertEquals(teamDetails.getTeamId(), teamDetailsArgumentCaptor.getValue().getTeamId());
    }
}