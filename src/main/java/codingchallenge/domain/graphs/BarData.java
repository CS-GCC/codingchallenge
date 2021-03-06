package codingchallenge.domain.graphs;

import java.text.DecimalFormat;
import java.util.OptionalDouble;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BarData {

    private int year;
    private double Q1;
    private String Q1Color = "#007788";
    private double Q2;
    private String Q2Color = "#159897";
    private double Q3;
    private String Q3Color = "#21ADA8";
    private double Q4;
    private String Q4Color = "#7AD7F0";
    private double Q5;
    private String Q5Color = "#92DFF3";
    private double Q6;
    private String Q6Color = "#B7E9F7";

    public BarData(int year, OptionalDouble q1, OptionalDouble q2, OptionalDouble q3,
                   OptionalDouble q4,
                   OptionalDouble q5,
                   OptionalDouble q6) {
        this.year = year;
        if (q1.isPresent()) {
            Q1 = round(q1.getAsDouble());
        } else {
            Q1 = 0.0;
        }
        if (q2.isPresent()) {
            Q2 = round(q2.getAsDouble());
        } else {
            Q2 = 0.0;
        }
        if (q3.isPresent()) {
            Q3 = round(q3.getAsDouble());
        } else {
            Q3 = 0.0;
        }
        if (q4.isPresent()) {
            Q4 = round(q4.getAsDouble());
        } else {
            Q4 = 0.0;
        }
        if (q5.isPresent()) {
            Q5 = round(q5.getAsDouble());
        } else {
            Q5 = 0.0;
        }
        if (q6.isPresent()) {
            Q6 = round(q6.getAsDouble());
        } else {
            Q6 = 0.0;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getQ1() {
        return Q1;
    }

    public void setQ1(double q1) {
        Q1 = q1;
    }

    public String getQ1Color() {
        return Q1Color;
    }

    public void setQ1Color(String q1Color) {
        Q1Color = q1Color;
    }

    public double getQ2() {
        return Q2;
    }

    public void setQ2(double q2) {
        Q2 = q2;
    }

    public String getQ2Color() {
        return Q2Color;
    }

    public void setQ2Color(String q2Color) {
        Q2Color = q2Color;
    }

    public double getQ3() {
        return Q3;
    }

    public void setQ3(double q3) {
        Q3 = q3;
    }

    public String getQ3Color() {
        return Q3Color;
    }

    public void setQ3Color(String q3Color) {
        Q3Color = q3Color;
    }

    public double getQ4() {
        return Q4;
    }

    public void setQ4(double q4) {
        Q4 = q4;
    }

    public String getQ4Color() {
        return Q4Color;
    }

    public void setQ4Color(String q4Color) {
        Q4Color = q4Color;
    }

    public double getQ5() {
        return Q5;
    }

    public void setQ5(double q5) {
        Q5 = q5;
    }

    public String getQ5Color() {
        return Q5Color;
    }

    public void setQ5Color(String q5Color) {
        Q5Color = q5Color;
    }

    public double getQ6() {
        return Q6;
    }

    public void setQ6(double q6) {
        Q6 = q6;
    }

    public String getQ6Color() {
        return Q6Color;
    }

    public void setQ6Color(String q6Color) {
        Q6Color = q6Color;
    }

    private static double round(double val) {
        val = val*100;
        val = Math.round(val);
        return val /100;
    }
}
