package com.zys.workflow;

import lombok.Data;

/**
 * workflow，工作流
 */
@Data
public class Workflow {

    private String workflowId;
    private String workflowName;
    /**
     * 根据code和type可以唯一确定一个工作流
     */
    private String type;
    private String code;
    /**
     * 由code和type唯一确定工作流的第一个activity
     */
    private String firstActivityId;

//    List<Activity> activities = new ArrayList<>(Constant.DEFAULT_SIZE);
//    List<Transition> transitions = new ArrayList<>(Constant.DEFAULT_SIZE);
//    List<StopCondition> stopConditions = new ArrayList<>(Constant.DEFAULT_SIZE);

}
