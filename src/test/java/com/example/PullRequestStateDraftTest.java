package com.example;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PullRequestStateDraftTest {

    @Test
    public void whenStateContainsNextStates() {

        List<PullRequestState> expected = List.of(PullRequestState.OPEN, PullRequestState.CLOSED);

        Assertions.assertEquals(expected, PullRequestState.DRAFT.possibleNextState());
    }

    @Test
    public void whenStateCanProceedToOpen() {

        Assertions.assertTrue(PullRequestState.DRAFT.canProccedToNextState(PullRequestState.OPEN));
    }

    @Test
    public void whenStateCanProceedToClosed() {

        Assertions.assertTrue(PullRequestState.DRAFT.canProccedToNextState(PullRequestState.CLOSED));
    }
    
    @Test
    public void whenStateCannotProceedToMerged() {

        Assertions.assertFalse(PullRequestState.DRAFT.canProccedToNextState(PullRequestState.MERGED));
    }

    @Test
    public void whenStateCannotProceedToDraft(){

        Assertions.assertFalse(PullRequestState.DRAFT.canProccedToNextState(PullRequestState.DRAFT));
    }
}
