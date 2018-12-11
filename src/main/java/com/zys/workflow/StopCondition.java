package com.zys.workflow;

import lombok.Data;

/**
 * 停止条件，命中后工作流停止
 */
@Data
public class StopCondition {
    private String workflowId;
    /**
     * 流程停止表达式
     */
    private String stopExpression;
    /**
     * 停止状态
     */
    private String stopState;
}
