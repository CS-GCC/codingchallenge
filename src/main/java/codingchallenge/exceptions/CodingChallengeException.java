package codingchallenge.exceptions;

public abstract class CodingChallengeException extends Exception {

    private final String errorMessage;

    public CodingChallengeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
