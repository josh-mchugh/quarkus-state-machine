package com.example;

import java.util.List;

public enum PullRequestState {
    DRAFT {
        @Override
        public List<PullRequestState> possibleNextState(){
            return List.of(OPEN, CLOSED);
        }
    },
    OPEN {
        @Override
        public List<PullRequestState> possibleNextState(){
            return List.of(MERGED, CLOSED);
        }
    },
    MERGED {
        @Override
        public List<PullRequestState> possibleNextState(){
            return List.of();
        }
    },
    CLOSED {
        @Override
        public List<PullRequestState> possibleNextState(){
            return List.of(OPEN);
        }
    };

    public abstract List<PullRequestState> possibleNextState();

    public boolean canProccedToNextState(PullRequestState nextState) {

        return this.possibleNextState().contains(nextState);
    }
}
