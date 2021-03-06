package com.zys.processor.impl;

import com.zys.processor.Processor;
import com.zys.workflow.TransactionContext;

import java.util.HashMap;
import java.util.Map;

public class CallProcessor implements Processor {

    private String processorId = "CallProcessor";
    private void beforeProcess(TransactionContext context) {
    }
    private void endProcess(TransactionContext context) {
        Map<String, Object> map = new HashMap<>();
        map.put("CallProcessor_State", "S");
        context.getContextMap().putAll(map);
    }

    @Override
    public void process(TransactionContext context) {
        beforeProcess(context);
        System.out.println("This is call processor process." + processorId);
        endProcess(context);
    }

}
