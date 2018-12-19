package com.zys.executor;

import com.zys.engine.EngineImpl;
import com.zys.workflow.TransactionContext;

/**
 * 引擎执行者
 */
public class EngineExcutor {
    //引擎 todo 后期单利实现
    EngineImpl engine = new EngineImpl();
    /**
     * 执行入口
     * @param code
     * @param type
     */
    public void execute (String code, String type) {
        preCheck(code, type);
        TransactionContext context = new TransactionContext();
        context.setCode(code);
        context.setType(type);
        engine.setTransactionContext(context);
        engine.run();
    }

    private void preCheck(String code, String type) {
        //1,check workflow exist.

        //2,check cycle
    }
}
