package com.zys.workflow;

import lombok.Data;

/**
 * 条件转换，命中后取得下一个activity
 */
@Data
public class Transition {
    private String workflowId;
    private String activityId;
    private String transitionExpression;
    private String nextActivityId;
}
