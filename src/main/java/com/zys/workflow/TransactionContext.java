package com.zys.workflow;

import com.zys.constant.Constant;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一次事务请求的上下文信息以及工作流流程引擎组件
 */
@Data
public class TransactionContext {
    private Workflow workflow;
    private Activity currentActivity;
    private List<Activity> activities = new ArrayList<>(Constant.DEFAULT_SIZE);
    private List<Transition> transitions = new ArrayList<>(Constant.DEFAULT_SIZE);;
    private List<StopCondition> stopConditions = new ArrayList<>(Constant.DEFAULT_SIZE);
    private Map<String, Object> contextMap = new HashMap<>(Constant.DEFAULT_SIZE);

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
    public void addStopCondition(StopCondition stopCondition) {
        stopConditions.add(stopCondition);
    }
    public void putIntoMap (String key, Object value) {
        contextMap.put(key, value);
    }

}
