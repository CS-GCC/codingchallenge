package codingchallenge.domain.graphs;

import java.util.List;

public class PosData {

    private String id;
    private List<Coordinates> data;

    public PosData(String id, List<Coordinates> data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Coordinates> getData() {
        return data;
    }

    public void setData(List<Coordinates> data) {
        this.data = data;
    }
}
