package codingchallenge.exceptions;

import codingchallenge.domain.subdomain.Category;

public class NotEnoughTestsException extends CodingChallengeException {

    private final Category category;
    private final int questionNumber;

    public NotEnoughTestsException(Category category, int questionNumber) {
        super("There were not enough tests to select from for this question");

        this.category = category;
        this.questionNumber = questionNumber;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }
}
