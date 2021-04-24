package com.share.springboot.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 获取当前时间类
 * @author Yu W
 * @date 2020/5/10 10:54
 */
public class GetDateUtil {
    public static Date getDateTime(){
        Date date =new Date();
        Timestamp timesTamp=new Timestamp(date.getTime());
        return timesTamp;
    }
}
