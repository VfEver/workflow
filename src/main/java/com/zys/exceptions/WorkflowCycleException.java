package com.zys.exceptions;

/**
 * 流程未配置异常
 * @author zys
 */
public class WorkflowCycleException extends RuntimeException {
    public WorkflowCycleException(String errorMsg) {
        super(errorMsg);
    }
}
