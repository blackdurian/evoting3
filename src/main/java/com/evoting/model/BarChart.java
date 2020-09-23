package com.evoting.model;

import java.util.List;

public class BarChart {
    List<String> label;
    List<Long> data;

    public BarChart(List<String> label, List<Long> data) {
        this.label = label;
        this.data = data;
    }

    public BarChart() {
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
