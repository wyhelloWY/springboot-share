package com.share.springboot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yu W
 * @date 2020/4/27 17:27
 */
public class PhoneFormat {
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

}
