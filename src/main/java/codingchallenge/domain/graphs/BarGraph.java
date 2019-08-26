package codingchallenge.domain.graphs;

import java.util.List;

public class BarGraph {
    private List<BarData> data;

    public BarGraph(List<BarData> data) {
        this.data = data;
    }

    public List<BarData> getData() {
        return data;
    }

    public void setData(List<BarData> data) {
        this.data = data;
    }
}
