package com.simpleMan.aaron;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "content_table")
public class Content{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String arab;

    private String penjelasan;

    //Construtor
    public Content(String arab, String penjelasan) {
        this.arab = arab;
        this.penjelasan = penjelasan;
    }

    //Setter for id
    public void setId(int id) {
        this.id = id;
    }

    //Getter
    public int getId() {
        return id;
    }

    public String getArab() {
        return arab;
    }

    public String getPenjelasan() {
        return penjelasan;
    }
}