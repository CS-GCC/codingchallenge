package codingchallenge.domain.graphs;

public class BubbleGraph {

    private BubbleData data;

    public BubbleGraph(BubbleData data) {
        this.data = data;
    }

    public BubbleData getData() {
        return data;
    }

    public void setData(BubbleData data) {
        this.data = data;
    }
}
