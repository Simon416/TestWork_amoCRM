package com.example.test_amocrm.data.pipeline;

public class Status {

    private String name;
    private int id;

    Status(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStatusName() {
        return name;
    }
}
