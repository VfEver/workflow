package com.zys.workflow;

import com.zys.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * workflow config，流程引擎的配置数据仓库
 */
public class WorkflowConfig {
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

}
