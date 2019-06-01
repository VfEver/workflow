package com.zys.executor;

import com.zys.engine.EngineImpl;
import com.zys.exceptions.WorkflowNotFoundException;
import com.zys.workflow.TransactionContext;
import com.zys.workflow.Workflow;
import com.zys.workflow.WorkflowConfig;

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
        preCheck(code, type);
        TransactionContext context = new TransactionContext();
        context.setCode(code);
        context.setType(type);
        context.setContextMap(Helper.Obj2Map(request));
        engine.setTransactionContext(context);
        engine.buildComponent();
        engine.run();
    }

    private void preCheck(String code, String type) throws Exception{
        //1,check workflow exist.
        Workflow workflow = WorkflowConfig.getWorkflow(code, type);
        if (workflow == null) {
            throw new WorkflowNotFoundException("Code:" + code + "type:" + type + " not config.");
        }

        //2,check cycle


    }
}


