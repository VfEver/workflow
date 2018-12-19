package com.zys.processor;

import com.zys.workflow.TransactionContext;

/**
 * activity下挂载processor，流程节点真正的执行者
 */
public interface Processor {
    void process(TransactionContext context);
}
