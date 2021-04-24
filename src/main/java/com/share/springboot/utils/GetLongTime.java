package com.share.springboot.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Yu W
 * @date 2020/5/10 12:51
 */
public class GetLongTime {
    public static String getLongStringTime(){
        Long time=new Date().getTime();
        String x = ""+time;
        return x;
    }
}
