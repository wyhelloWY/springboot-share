package com.share.springboot.controller;

import com.share.springboot.entity.ActivityList;
import com.share.springboot.service.ActivityListService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.GetDateUtil;
import com.share.springboot.utils.GetLongTime;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Yu W
 * @date 2020/5/8 20:37
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityListService activityListService;
    @Autowired
    private UserListService userListService;
    @RequestMapping("/toActivityDetail/{act_id}")
    public String toActivityDetail(@PathVariable("act_id") String act_id, Model model){
//        System.out.println(act_id);
//        System.out.println("jinru");
        ActivityList activityList = new ActivityList();
        activityList = activityListService.selectById(act_id);
        activityList.setUser_name(userListService.selectUsername(activityList.getActivity_organizer_id()));
        model.addAttribute("activityList",activityList);
//        System.out.println(activityList);
        return "activity_into_pic";

    }

    @RequestMapping("/getActivity")
    @ResponseBody
    public List<ActivityList> getActivity(){

        List<ActivityList> activityLists = new ArrayList<>();
        activityLists = activityListService.selectAllContent();
        for (ActivityList activityList:activityLists){
            String a = activityList.getActivity_organizer_id();
            String b = userListService.selectUsername(a);
            activityList.setUser_name(b);
        }
        return activityLists;
    }

    @RequestMapping("/getFourActivity")
    @ResponseBody
    public List<ActivityList> getFourActivity(){
        List<ActivityList> activityLists = new ArrayList<>();
        activityLists = activityListService.selectAllContentPeople();
        for (ActivityList activityList:activityLists){
            String a = activityList.getActivity_organizer_id();
            String b = userListService.selectUsername(a);
            activityList.setUser_name(b);
        }
        return activityLists;
    }

    @RequestMapping("/postActivity")
    public ModelAndView postActivity(@RequestParam(value = "image_name",required = false)MultipartFile image_name, String activity_name, String activity_content, HttpSession session)
            throws IOException {
        System.out.println(image_name);
        String user_phone =(String)session.getAttribute("user_phone");
        if (image_name!=null){
            //获取原始图片的拓展名
            String originalFilename = image_name.getOriginalFilename();
//            System.out.println(originalFilename);
            //新的文件名字
            String newFileName = UUID.randomUUID() + originalFilename;
            System.out.println(newFileName);
            //封装上传文件位置的全路径
            String file_path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/pictures/";
//            String file_path ="/root/photo/";
            String path = file_path+newFileName;
            // File targetFile = new File(file_path, newFileName);
            FileUtils.copyInputStreamToFile(image_name.getInputStream(), new File(file_path, newFileName));
            //插入图片
            saveActivity(newFileName,user_phone,activity_name,activity_content);
            return new ModelAndView("index");

        }else{
            return new ModelAndView("error");
        }

    }

    public void saveActivity(String filename,String user_phone,String name,String content){
        String activity_id = user_phone + GetLongTime.getLongStringTime();
        ActivityList activityList = new ActivityList();
        activityList.setImage_file(filename);
        //插入时间
        activityList.setActivity_date(GetDateUtil.getDateTime());
        //插入活动id
        activityList.setActivity_id(activity_id);
        //插入活动发布人
        activityList.setActivity_organizer_id(user_phone);
        //插入活动name
        activityList.setActivity_name(name);
        activityList.setActivity_content(content);
        activityListService.insertOne(activityList);
    }



}
