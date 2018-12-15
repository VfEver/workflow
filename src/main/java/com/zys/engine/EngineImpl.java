package com.zys.engine;

import com.zys.processor.Processor;
import com.zys.processor.Processors;
import com.zys.workflow.Activity;
import com.zys.workflow.TransactionContext;
import com.zys.workflow.WorkflowConfig;

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
    public void buildComponent() {
        transactionContext.setStep(0);
        fillWorkflow(transactionContext);
        fillActivities(transactionContext);
        fillStopConditions(transactionContext);
        fillTransitions(transactionContext);
    }

    private void fillWorkflow (TransactionContext transactionContext) {
        transactionContext.setWorkflow(WorkflowConfig.getWorkflow(transactionContext.getCode(), transactionContext.getType()));
    }

    private void fillActivities(TransactionContext transactionContext) {
        transactionContext.getActivities()
                .addAll(WorkflowConfig.getActivities(transactionContext.getWorkflow().getWorkflowId()));
    }

    private void fillStopConditions(TransactionContext transactionContext) {
        transactionContext.getStopConditions()
                .addAll(WorkflowConfig.getStopConditions(transactionContext.getWorkflow().getWorkflowId()));
    }

    private void fillTransitions(TransactionContext transactionContext) {
        transactionContext.getTransitions()
                .addAll(WorkflowConfig.getTransitions(transactionContext.getWorkflow().getWorkflowId()));
    }

    @Override
    public void run() {
        Activity currentActivity = transactionContext.getCurrentActivity();
        Processor processor = Processors.getSpecProcessor(currentActivity.getProcessName());
        processor.process();
    }
}
