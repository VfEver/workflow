package com.zys.executor;

import com.zys.engine.EngineImpl;
import com.zys.exceptions.StopConditionNotFoundException;
import com.zys.exceptions.WorkflowCycleException;
import com.zys.exceptions.WorkflowNotFoundException;
import com.zys.utils.Helper;
import com.zys.workflow.*;

import java.util.List;

/**
 * 引擎执行者
 */
public class EngineExcutor {
    //引擎
    private EngineImpl engine = new EngineImpl();
    /**
     * 执行入口
     * @param code
     * @param type
     */
    public void execute (String code, String type, Object request) throws Exception{
        TransactionContext context = new TransactionContext();
        context.setCode(code);
        context.setType(type);
        context.setContextMap(Helper.Obj2Map(request));
        engine.setTransactionContext(context);
        engine.buildComponent();
        preCheck(code, type);
        engine.run();
    }
    private void preCheck(String code, String type) throws Exception{
        //1,check workflow exist.
        Workflow workflow = WorkflowConfig.getWorkflow(code, type);
        if (workflow == null) {
            throw new WorkflowNotFoundException("Code:" + code + " Type:" + type + " not config.");
        }
        //2,stop condition check
        List<StopCondition> stopConditions = engine.getTransactionContext().getStopConditions();
        if (stopConditions.isEmpty()) {
            throw new StopConditionNotFoundException("Workflow:" + workflow.getWorkflowId() + " Stop Condition Not Found.");
        }
        //3,cycle check
        List<Transition> transitions = engine.getTransactionContext().getTransitions();
        for (Transition transition : transitions) {
            if (transition.getActivityId().equals(transition.getNextActivityId())
                    && !transition.getTransitionExpression().contains("Step")) {
                throw new WorkflowCycleException("Workflow:" +
                        workflow.getWorkflowId() +
                        " Transition:" +
                        transition.getTransitionExpression() +
                        " is Cycle");
            }
        }
    }
}


