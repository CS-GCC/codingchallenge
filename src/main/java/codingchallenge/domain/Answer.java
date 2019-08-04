package codingchallenge.domain;

import codingchallenge.domain.subdomain.Correctness;
import org.springframework.data.annotation.Id;

public class Answer {

    @Id
    private String id;
    private int questionNumber;
    private int testNumber;
    private Correctness correct;
    private String contestant;
    private int expected;
    private int result;
    private double speed;

    public Answer() {
    }

    public Answer(int questionNumber, int testNumber, Correctness correct,
                  String contestant, double speed) {
        this.questionNumber = questionNumber;
        this.testNumber = testNumber;
        this.correct = correct;
        this.contestant = contestant;
        this.speed = speed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public Correctness getCorrect() {
        return correct;
    }

    public void setCorrect(Correctness correct) {
        this.correct = correct;
    }

    public String getContestant() {
        return contestant;
    }

    public void setContestant(String contestant) {
        this.contestant = contestant;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
