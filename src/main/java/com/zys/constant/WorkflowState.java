package com.zys.constant;

/**
 * 流程引擎状态机
 */
public enum  WorkflowState {

    P("P", "处理中"),
    F("F", "失败"),
    S("S", "成功"),
    ;
    private String state;
    private String msg;
    private WorkflowState(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }
}
