package codingchallenge.domain.subdomain;

public enum Category {
    SMALL(75),
    MEDIUM(15),
    LARGE(10);

    private final int numberOfTests;

    Category(int numberOfTests) {
        this.numberOfTests = numberOfTests;
    }

    public int getNumberOfTests() {
        return numberOfTests;
    }
}
