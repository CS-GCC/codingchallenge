package codingchallenge.domain.subdomain;

public enum Category {
    SMALL(50, 1.0),
    MEDIUM(10, 2.0),
    LARGE(6, 5.0);

    private final int numberOfTests;
    private final double testValue;

    Category(int numberOfTests, double testValue) {
        this.numberOfTests = numberOfTests;
        this.testValue = testValue;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }

    public double getTestValue() {
        return testValue;
    }
}
