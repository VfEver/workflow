package com.zys.processor.impl;

import com.zys.processor.Processor;
import com.zys.workflow.TransactionContext;

public class CallProcessor implements Processor {

    private String processorId = "CallProcessor";
    private void beforeProcess(TransactionContext context) {
    }
    private void endProcess(TransactionContext context) {
    }

    @Override
    public void process(TransactionContext context) {
        beforeProcess(context);
        System.out.println("This is call processor process." + processorId);
        endProcess(context);
    }

}
