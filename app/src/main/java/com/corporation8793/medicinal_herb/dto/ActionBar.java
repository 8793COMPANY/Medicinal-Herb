package com.corporation8793.medicinal_herb.dto;

public class ActionBar {

    public final String title;
    public final int color;
    public ActionBar(String title, int color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

}
