package com.zys.engine;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.zys.constant.WorkflowState;
import com.zys.exceptions.ActivityNotFoundException;
import com.zys.processor.Processors;
import com.zys.workflow.*;

import java.util.List;
import java.util.Map;

import static com.zys.constant.Constant.*;

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
        transactionContext.setEngineCurrentStep(0);
        transactionContext.setEngineTotalStep(0);
        transactionContext.setWorkflowState(WorkflowState.P.name());
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
        System.out.println("ActivityHistory：" + transactionContext.getActivityHistory());
        System.out.println("WorkflowState:" + transactionContext.getWorkflowState());
        System.out.println("EngineCurrentStep：" + transactionContext.getEngineCurrentStep());
        System.out.println("EngineTotalStep：" + transactionContext.getEngineTotalStep());
    }

    /***
     * 引擎调用前，设置当前执行步数和总执行步数
     * 后期考虑将当前执行步数和总执行部署提取成为
     */
    private void beforeRun() {
        //设置执行步数
        setExecuteStep();
    }

    private void setExecuteStep() {
        //引擎总执行步数
        if (!transactionContext.getContextMap().containsKey(ENGINETOTALSTEP)) {
            transactionContext.getContextMap().put(ENGINETOTALSTEP, 1);
        }
        //引擎当前执行步数
        transactionContext.getContextMap().put(ENGINECURRENTSTEP, 1);
    }

    /**
     * 引擎总执行次数和当前执行次数都增加
     */
    private void addExecuteStep() {
        //引擎总执行步数
        Object engineTotalStep = transactionContext.getContextMap().get(ENGINETOTALSTEP);
        //引擎当前执行步数
        Object engineCurrentStep = transactionContext.getContextMap().get(ENGINECURRENTSTEP);

        engineTotalStep = (Integer)engineTotalStep + 1;
        engineCurrentStep = (Integer)engineCurrentStep + 1;
        //引擎总执行步数
        transactionContext.getContextMap().put(ENGINETOTALSTEP, engineTotalStep);
        //引擎当前执行步数
        transactionContext.getContextMap().put(ENGINECURRENTSTEP, engineCurrentStep);
    }

    /**
     * 引擎调用后
     * 1，将引擎当前执行的结果写入到上下文信息中
     * 2，判断引擎是否命中停止条件
     * @return
     */
    private boolean endRun() {
        //判断是否到达终止条件
        Map<String, Object> contextMap = transactionContext.getContextMap();
        List<StopCondition> stopConditions = transactionContext.getStopConditions();
        for (StopCondition stopCondition : stopConditions) {
            Expression compiledExp = AviatorEvaluator.compile(stopCondition.getStopExpression());
            boolean stop = (boolean)compiledExp.execute(contextMap);
            //命中停止条件，设置引擎的执行状态
            if (stop) {
                transactionContext.setWorkflowState(stopCondition.getStopState());
                return false;
            }
        }
        return false;
    }

    /**
     * 引擎执行一次
     */
    private void runOnce() {
        //执行前先获取即将执行的activity
        boolean needContinue = setCurrentActivity();
        //需要执行
        //执行前记录执行状态N，执行后记录执行状态S
        //执行完毕执行次数需要增加
        if(needContinue) {
            setHisBeforeProcess();
            Processors.getSpecProcessor(transactionContext.getCurrentActivity().getProcessId()).process(transactionContext);
            setHisEndProcess();
            addExecuteStep();
        }
    }

    private void setHisEndProcess() {
        Integer totalStep = (Integer)transactionContext.getContextMap().get(ENGINETOTALSTEP);
        setActivitiesHis(totalStep, ACTIVITY_SUCCESS);
    }

    private void setHisBeforeProcess() {
        Integer totalStep = (Integer)transactionContext.getContextMap().get(ENGINETOTALSTEP);
        setActivitiesHis(totalStep-1, ACTIVITY_PROCESS);
    }

    private void setActivitiesHis (int index, String activityState) {
        String activityHistory = transactionContext.getActivityHistory();
        if (activityHistory.length() > ZERO) {
            activityHistory += VERTICAL_SEGMENTER;
        }
        Activity currentActivity = transactionContext.getCurrentActivity();
        activityHistory += index + COMMA_SEGMENTER +
                currentActivity.getActivityId() + COMMA_SEGMENTER +
                currentActivity.getProcessId() + COMMA_SEGMENTER+
                activityState;
        transactionContext.setActivityHistory(activityHistory);
    }

    /**
     * 设置当前即将执行的流程节点
     * @return
     */
    private boolean setCurrentActivity() {
        Map<String, Object> contextMap = transactionContext.getContextMap();
        Object totalStep = contextMap.get(ENGINETOTALSTEP);
        //说明是第一次进入
        if((Integer)totalStep == 1) {
            String firstActivityId = transactionContext.getWorkflow().getFirstActivityId();
            Activity currentActivity = WorkflowConfig.getActivityById(firstActivityId);
            if (currentActivity == null) {
                throw new ActivityNotFoundException("First Activity is Null.");
            }
            transactionContext.setCurrentActivity(currentActivity);
            return true;
        } else {  //非第一次进入，开始计算
            List<Activity> activities = transactionContext.getActivities();
            List<Transition> transitions = transactionContext.getTransitions();
            for (Transition transition : transitions) {
                Expression compiledExp = AviatorEvaluator.compile(transition.getTransitionExpression());
                if ((boolean)compiledExp.execute(contextMap)) {
                    for (Activity activity : activities) {
                        if (activity.getActivityId().equals(transition.getActivityId())){
                            transactionContext.setCurrentActivity(activity);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
