package com.zys.processor;

import com.zys.constant.Constant;
import com.zys.processor.impl.CallProcessor;
import com.zys.processor.impl.ChannelProcessor;

import java.util.HashMap;
import java.util.Map;
/**
 * processor registor
 */
public class Processors {
    private static Map<String, Processor> processorsMap = new HashMap<>(Constant.DEFAULT_SIZE);
    static {
        registProcess("CallProcessor", new CallProcessor());
        registProcess("ChannelProcessor", new ChannelProcessor());
    }
    private static void registProcess (String processId, Processor processor) {
        processorsMap.put(processId, processor);
    }
    public static Processor getSpecProcessor (String processName) {
        return processorsMap.get(processName);
    }
}
