package com.omninos.nani.modelClass;

/**
 * Created by Manjinder Singh on 26 , November , 2019
 */
public class MyAllergiesModel {
    String name, id;

    public MyAllergiesModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
