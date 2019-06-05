package com.zys.exceptions;

/**
 * 流程未配置异常
 * @author zys
 */
public class StopConditionNotFoundException extends RuntimeException {
    public StopConditionNotFoundException(String errorMsg) {
        super(errorMsg);
    }
}
