package codingchallenge.exceptions;

public class GitRepoNotFoundException extends CodingChallengeException {

    private final String repoName;

    public GitRepoNotFoundException(String repoName) {
        super("Couldn't find repository");
        this.repoName = repoName;
    }

    public String getRepoName() {
        return repoName;
    }
}
