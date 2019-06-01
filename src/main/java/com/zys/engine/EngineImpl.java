package com.zys.engine;

import com.zys.exceptions.ActivityNotFoundException;
import com.zys.processor.Processor;
import com.zys.processor.Processors;
import com.zys.workflow.Activity;
import com.zys.workflow.TransactionContext;
import com.zys.workflow.WorkflowConfig;

import java.util.Map;

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
        beforeRun();
        do {
            runOnce();
        } while (endRun());
    }

    /***
     * 引擎调用前
     */
    private void beforeRun() {
        //设置执行步数
        setExecuteStep();
    }

    private void setExecuteStep() {
        //引擎总执行步数
        transactionContext.getContextMap().put("EngineTotalStep", 1);
        //引擎当前执行步数
        transactionContext.getContextMap().put("EngineCurrentStep", 1);
    }

    /**
     * 引擎调用后
     * @return
     */
    private boolean endRun() {
        //判断是否到达终止条件
        return true;
    }

    /**
     * 引擎执行一次
     */
    private void runOnce() {
        setCurrentActivity();
        Processor processor = Processors.getSpecProcessor(transactionContext.getCurrentActivity().getProcessName());
        processor.process(transactionContext);
    }

    private void setCurrentActivity() {
        Map<String, Object> contextMap = transactionContext.getContextMap();
        Object totalStep = contextMap.get("EngineTotalStep");
        //说明是第一次进入
        if((Integer)totalStep == 1) {
            String firstActivityId = transactionContext.getWorkflow().getFirstActivityId();
            Activity currentActivity = WorkflowConfig.getActivityById(firstActivityId);
            if (currentActivity == null) {
                throw new ActivityNotFoundException("first activity is null.");
            }
            transactionContext.setCurrentActivity(currentActivity);
        } else {  //非第一次进入，开始计算

        }
    }

    private String getNextActivityId(TransactionContext transactionContext) {
        Map<String, Object> contextMap = transactionContext.getContextMap();
        
        return "";
    }
}
