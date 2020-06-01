package com.simpleMan.aaron;

public class AudioModel {

    private int id;
    private String penjelasan;

    public AudioModel(int id, String penjelasan) {
        this.id = id;
        this.penjelasan = penjelasan;
    }

    public int getId() {
        return id;
    }

    public String getPenjelasan() {
        return penjelasan;
    }
}
