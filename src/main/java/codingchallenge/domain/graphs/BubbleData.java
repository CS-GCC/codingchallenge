package codingchallenge.domain.graphs;

import java.util.List;

public class BubbleData {

    private String name;
    private List<BubbleData> children;
    private double value;

    public BubbleData(String name, List<BubbleData> children, double value) {
        this.name = name;
        this.children = children;
        this.value = round(value);
    }

    public BubbleData(String name, List<BubbleData> children) {
        this.name = name;
        this.children = children;
    }

    public BubbleData(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BubbleData> getChildren() {
        return children;
    }

    public void setChildren(List<BubbleData> children) {
        this.children = children;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private static double round(double val) {
        val = val*100;
        val = Math.round(val);
        return val /100;
    }
}
