package codingchallenge.exceptions;

public class ContestantNotFoundException extends CodingChallengeException {

    private final String contestantId;

    public ContestantNotFoundException(String contestantId) {
        super("No contestant could be found");
        this.contestantId = contestantId;
    }
}
