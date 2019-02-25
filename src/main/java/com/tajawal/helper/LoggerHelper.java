package com.tajawal.helper;

import org.apache.log4j.Logger;


@SuppressWarnings("rawtypes")
public class LoggerHelper {

    public static Logger getLogger(Class cls) {

        return Logger.getLogger(cls);

    }

}
