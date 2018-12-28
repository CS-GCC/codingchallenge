package codingchallenge.domain;

import org.springframework.data.annotation.Id;

public class Question {

    @Id
    private String id;
    private int questionNumber;
    private String questionText;
    private String questionName;
    private boolean active;

    public Question() {
    }

    public Question(int questionNumber, String questionText, String questionName, boolean active) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.questionName = questionName;
        this.active = active;
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

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
