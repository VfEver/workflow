package com.zys.processor;

import com.zys.constant.Constant;

import java.util.HashMap;
import java.util.Map;


/**
 * processor registor
 */
public class Processors {
    private static Map<String, Processor> processorsMap = new HashMap<>(Constant.DEFAULT_SIZE);
    static {

        processorsMap.put("null", null);
    }
    public void registProcess (String processId, Processor processor) {
        processorsMap.put(processId, processor);
    }
    public static Processor getSpecProcessor (String processName) {
        return processorsMap.get(processName);
    }
}
