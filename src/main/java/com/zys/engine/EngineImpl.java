package com.zys.engine;

import com.zys.processor.Processors;
import com.zys.workflow.Activity;
import com.zys.workflow.TransactionContext;

/**
 * 引擎具体实现
 */
public class EngineImpl implements Engine {

    private TransactionContext transactionContext;

    public void setTransactionContext(TransactionContext transactionContext) {
        this.transactionContext = transactionContext;
    }

    public TransactionContext getTransactionContext() {
        return transactionContext;
    }

    @Override
    public boolean cycleCheck() {
        return false;
    }

    @Override
    public void run() {
        Activity currentActivity = transactionContext.getCurrentActivity();
        Processors.getSpecProcessor(currentActivity.getProcessName());
    }
}
