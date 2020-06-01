package com.simpleMan.aaron;

public class Model {

    private int id;
    private String arab;
    private String penjelasan;

    public Model(int id, String arab, String penjelasan) {
        this.id = id;
        this.arab = arab;
        this.penjelasan = penjelasan;
    }

    public int getId() {
        return id;
    }

    public String getArabtajwid() {
        return arab;
    }

    public String getPenjelasantajwid() {
        return penjelasan;
    }
}
