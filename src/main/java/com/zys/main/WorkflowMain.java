package com.zys.main;

import com.zys.executor.EngineExcutor;
import com.zys.workflow.WorkflowConfig;
import lombok.Data;

public class WorkflowMain {

    public static void main(String[] args) throws Exception{

        WorkflowConfig.initWorkflow("test");
        EngineExcutor executor = new EngineExcutor();
        Request request = new Request();
        request.setAge(25);
        request.setScore(100.00);
        request.setName("luwenwen");
        executor.execute("pay", "direct", request);
    }
}

@Data
class Request {
    private int age;
    private double score;
    private String name;
}