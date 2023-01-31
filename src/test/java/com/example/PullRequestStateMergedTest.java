package com.example;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PullRequestStateMergedTest {
    
    @Test
    public void whenStateContainsNextStates() {

        Assertions.assertEquals(List.of(), PullRequestState.MERGED.possibleNextState());
    }

    @Test
    public void whenStatCannotProceedToDraft() {

        Assertions.assertFalse(PullRequestState.MERGED.canProccedToNextState(PullRequestState.DRAFT));
    }

    @Test
    public void whenStateCannotProceedToOpen() {

        Assertions.assertFalse(PullRequestState.MERGED.canProccedToNextState(PullRequestState.OPEN));
    }

    @Test
    public void whenStateCannotProceedToClosed() {

        Assertions.assertFalse(PullRequestState.MERGED.canProccedToNextState(PullRequestState.CLOSED));
    }

    @Test
    public void whenStateCannotProceedToMerged() {

        Assertions.assertFalse(PullRequestState.MERGED.canProccedToNextState(PullRequestState.MERGED));
    }
}
