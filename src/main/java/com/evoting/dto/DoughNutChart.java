package com.evoting.dto;

import java.util.List;

public class DoughNutChart {
    String name;
    List<String> label;
    List<Integer> data;
    List<String> party;

    public DoughNutChart(String name, List<String> label, List<Integer> data, List<String> party) {
        this.name = name;
        this.label = label;
        this.data = data;
        this.party = party;
    }
}
