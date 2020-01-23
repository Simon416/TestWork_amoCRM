package com.example.test_amocrm.data.pipeline;

import java.util.List;

public class Pipeline {

    private int id;
    private String name;
    private List<Status> listStatuses;

    Pipeline(int id, String name, List<Status> listStatuses) {
        this.id = id;
        this.name = name;
        this.listStatuses = listStatuses;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Status> getListStatuses() {
        return listStatuses;
    }

}
