package com.ydova;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    public static void info(String msg, Class<?> logger) {
        Logger log = LoggerFactory.getLogger(logger);
        log.info(msg);
    }


    public static void error(String msg) {
        Logger log = LoggerFactory.getLogger(Log.class);
        log.info(msg);
        log.error(msg);
    }
}
