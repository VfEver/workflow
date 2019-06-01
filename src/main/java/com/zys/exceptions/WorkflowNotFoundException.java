package com.zys.exceptions;

/**
 * 流程未配置异常
 * @author zys
 */
public class WorkflowNotFoundException extends RuntimeException {
    public WorkflowNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
