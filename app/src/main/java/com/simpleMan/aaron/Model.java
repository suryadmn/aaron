package com.simpleMan.aaron;

public class Model {

    private int id;
    private String arabtajwid;
    private String penjelasantajwid;

    public Model(int id, String arabtajwid, String penjelasantajwid) {
        this.id = id;
        this.arabtajwid = arabtajwid;
        this.penjelasantajwid = penjelasantajwid;
    }

    public int getId() {
        return id;
    }

    public String getArabtajwid() {
        return arabtajwid;
    }

    public String getPenjelasantajwid() {
        return penjelasantajwid;
    }
}
