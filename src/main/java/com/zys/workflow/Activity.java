package com.zys.workflow;

import lombok.Data;

/**
 * 工作流中的流程节点
 */
@Data
public class Activity {
    private String workflowId;
    private String activityId;
    private String processId;
}
