package com.share.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yu W
 * @date 2020/5/6 14:16
 */
@Controller
public class IndexController {
    @RequestMapping("/login")
    public String index(){
        return "login";
    }
    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/toActivity")
    public String toActivity(){
        return "Activities";
    }
    @RequestMapping("/toLoginIn")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/toInfoIn")
    public String toInfo(){
        return "info";
    }
    @RequestMapping("/activity_into_pic")
    public String toActivity_into_pic(){
        return "activity_into_pic";
    }
    @RequestMapping("/pic_content")
    public String toPic_content(){
        return "pic_content";
    }
//    @RequestMapping("/activity_into_pic")
//    public String toActivity_into_pic(){
//        return "activity_into_pic";
//    }
}
