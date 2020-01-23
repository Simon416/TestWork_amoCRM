package com.example.test_amocrm.ui.main.main;

public class DealUi {
    private String name;
    private String statusName;
    private int sale;
    private long dataCreate;

    DealUi(String name, int sale, long dataCreate, String statusName) {
        this.name = name;
        this.statusName = statusName;
        this.sale = sale;
        this.dataCreate = dataCreate;
    }

    public String getName() {
        return name;
    }

    String getStatusName() {
        return statusName;
    }

    int getSale() {
        return sale;
    }

    long getDataCreate() {
        return dataCreate;
    }
}
