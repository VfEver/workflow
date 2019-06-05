package com.zys.processor.impl;

import com.zys.processor.Processor;
import com.zys.workflow.TransactionContext;

import java.util.HashMap;
import java.util.Map;

public class ChannelProcessor  implements Processor {
    private String processorId = "ChannelProcessor";
    private void beforeProcess(TransactionContext context) {
    }
    private void endProcess(TransactionContext context) {
        Map<String, Object> map = new HashMap<>();
        map.put("ChannelProcessor_State", "S");
        context.getContextMap().putAll(map);
    }

    @Override
    public void process(TransactionContext context) {
        beforeProcess(context);
        System.out.println("This is channel processor process." + processorId);
        endProcess(context);
    }
}
