package com.example.test_amocrm.data.pipeline;

import java.util.List;


public class Pipelines {
    private List<Pipeline> listPipeline;

    Pipelines(List<Pipeline> listPipeline) {
        this.listPipeline = listPipeline;
    }

    public List<Pipeline> getListPipeline() {
        return listPipeline;
    }
}
