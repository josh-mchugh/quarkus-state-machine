package com.example;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PullRequestStateClosedTest {
    
    @Test
    public void whenStateContainsNextStates() {

        List<PullRequestState> expected = List.of(PullRequestState.OPEN);

        Assertions.assertEquals(expected, PullRequestState.CLOSED.possibleNextState());
    }

    @Test
    public void whenStateCannotProceedToDraft() {

        Assertions.assertFalse(PullRequestState.CLOSED.canProccedToNextState(PullRequestState.DRAFT));
    }

    @Test
    public void whenStateCanProceedToOpen() {

        Assertions.assertTrue(PullRequestState.CLOSED.canProccedToNextState(PullRequestState.OPEN));
    }

    @Test
    public void whenStateCannotProceedToMerged() {

        Assertions.assertFalse(PullRequestState.CLOSED.canProccedToNextState(PullRequestState.MERGED));
    }

    @Test
    public void whenStateCannotProceedToClosed() {

        Assertions.assertFalse(PullRequestState.CLOSED.canProccedToNextState(PullRequestState.CLOSED));
    }
}
