package com.zys.exceptions;

/**
 * activity not find exception
 * @author zys
 */
public class ActivityNotFoundException extends RuntimeException{
    public ActivityNotFoundException (String errorMsg) {
        super(errorMsg);
    }
}
