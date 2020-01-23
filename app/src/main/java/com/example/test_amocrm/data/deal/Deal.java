package com.example.test_amocrm.data.deal;

public class Deal {

    private String name;
    private int sale;
    private long dataCreate;
    private int  pipelineId;
    private int statusId;

    public long getDataCreate() {
        return dataCreate;
    }

    void setDataCreate(long dataCreate) {
        this.dataCreate = dataCreate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSale() {
        return sale;
    }

    void setSale(int sale) {
        this.sale = sale;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getStatusId() {
        return statusId;
    }

    void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
