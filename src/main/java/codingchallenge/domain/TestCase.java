package codingchallenge.domain;

import codingchallenge.domain.subdomain.Category;
import org.springframework.data.annotation.Id;

public class TestCase {

    @Id
    private String id;
    private int questionNumber;
    private int testNumber;
    private Category category;
    private String input;
    private int output;

    public TestCase() {
    }

    public TestCase(int questionNumber, int testNumber, Category category, String input, int output) {
        this.questionNumber = questionNumber;
        this.testNumber = testNumber;
        this.category = category;
        this.input = input;
        this.output = output;
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

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
