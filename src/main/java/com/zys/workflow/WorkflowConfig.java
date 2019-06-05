package com.zys.workflow;

import com.zys.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * workflow config，流程引擎的配置数据仓库
 */
public class WorkflowConfig {
    private static List<Workflow> workflows = new ArrayList<>(Constant.DEFAULT_SIZE);
    private static List<Activity> activities = new ArrayList<>(Constant.DEFAULT_SIZE);
    private static List<Transition> transitions = new ArrayList<>(Constant.DEFAULT_SIZE);;
    private static List<StopCondition> stopConditions = new ArrayList<>(Constant.DEFAULT_SIZE);
    public static void initWorkflow (String initType) {
        switch (initType) {
            case Constant.CONFIG_DB: {
                initFromConfigDB();
                break;
            }
            case Constant.CONFIG_FILE: {
                initFromConfigFile();
                break;
            }
            default: {
                initFromHardCode();
            }
        }
    }

    /**
     * 从配置文件加载
     */
    private static void initFromConfigFile () {
    }

    /**
     * 从db加载
     */
    private static void initFromConfigDB () {
    }

    /**
     * 硬编码加载，测试使用
     */
    private static void initFromHardCode() {
        Workflow workflow = new Workflow();
        workflow.setWorkflowId("001");
        workflow.setWorkflowName("test");
        workflow.setCode("pay");
        workflow.setType("direct");
        workflow.setFirstActivityId("call");
        workflows.add(workflow);

        Activity callActivity = new Activity();
        callActivity.setActivityId("call");
        callActivity.setProcessId("CallProcessor");
        callActivity.setWorkflowId("001");
        activities.add(callActivity);

        Activity channelActivity = new Activity();
        channelActivity.setActivityId("ali");
        channelActivity.setProcessId("ChannelProcessor");
        channelActivity.setWorkflowId("001");
        activities.add(channelActivity);

        StopCondition stopCondition = new StopCondition();
        stopCondition.setWorkflowId("001");
        stopCondition.setStopState("S");
        stopCondition.setStopExpression("ChannelProcessor_State==S");
        stopConditions.add(stopCondition);

        Transition transition = new Transition();
        transition.setActivityId(callActivity.getActivityId());
        transition.setNextActivityId(channelActivity.getActivityId());
        transition.setWorkflowId("001");
        transition.setTransitionExpression("CallProcessor_State==S");
        transitions.add(transition);
    }

    public static Workflow getWorkflow (String code, String type) {
        for (Workflow workflow : workflows) {
            if (workflow.getCode().equals(code) && workflow.getType().equals(type)) {
                return workflow;
            }
        }
        return null;
    }

    public static List<Activity> getActivities (String workflowId) {
        List<Activity> targetActivities = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getWorkflowId().equals(workflowId)) {
                targetActivities.add(activity);
            }
        }
        return targetActivities;
    }
    public static List<StopCondition> getStopConditions (String workflowId) {
        List<StopCondition> targetStopConditions = new ArrayList<>();
        for (StopCondition stopCondition : stopConditions) {
            if (stopCondition.getWorkflowId().equals(workflowId)) {
                targetStopConditions.add(stopCondition);
            }
        }
        return targetStopConditions;
    }

    public static List<Transition> getTransitions (String workflowId) {
        List<Transition> tragetTransitions = new ArrayList<>();
        for (Transition transition : tragetTransitions) {
            if (transition.getWorkflowId().equals(workflowId)) {
                tragetTransitions.add(transition);
            }
        }
        return tragetTransitions;
    }

    public static Activity getActivityById (String activityId) {
        for (Activity activity : activities) {
            if (activity.getActivityId().equals(activityId)) {
                return activity;
            }
        }
        return null;
    }

}
