package codingchallenge.domain.subdomain;

import java.util.Objects;

public class Score {

    private int questionNumber;
    private int correct;
    private int incorrect;
    private int timedOut;
    private double total;

    public Score() {}

    public Score(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Score(int questionNumber, int correct, int incorrect, int timedOut
            , double total) {
        this.questionNumber = questionNumber;
        this.correct = correct;
        this.incorrect = incorrect;
        this.timedOut = timedOut;
        this.total = total;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getTimedOut() {
        return timedOut;
    }

    public void setTimedOut(int timedOut) {
        this.timedOut = timedOut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void increaseTotal(double total) {
        this.total += total;
    }

    public void incrementCorrect() {
        this.correct++;
    }

    public void incrementIncorrect() {
        this.incorrect++;
    }

    public void incrementTimedOut() {
        this.timedOut++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return questionNumber == score.questionNumber &&
                correct == score.correct &&
                incorrect == score.incorrect &&
                timedOut == score.timedOut &&
                Double.compare(score.total, total) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionNumber, correct, incorrect, timedOut, total);
    }
}
