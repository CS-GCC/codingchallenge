package codingchallenge.domain.graphs;

import java.util.List;

public class PosGraph {

    private int max;
    private int min;
    private List<PosData> data;

    public PosGraph(int max, int min, List<PosData> data) {
        this.max = max;
        this.min = min;
        this.data = data;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public List<PosData> getData() {
        return data;
    }

    public void setData(List<PosData> data) {
        this.data = data;
    }
}
