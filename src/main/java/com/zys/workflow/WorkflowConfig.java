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
                initFromConfigFile();
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
            if (activity.getActivityId().equals(workflowId)) {
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
}