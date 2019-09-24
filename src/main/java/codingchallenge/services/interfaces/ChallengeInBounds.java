package codingchallenge.services.interfaces;

import codingchallenge.domain.Status;

public interface ChallengeInBounds {

    Status challengeInBounds();

    boolean isStubbed();

    boolean isGitEnabled();

}
