package com.share.springboot.controller;

import com.alibaba.druid.sql.dialect.sqlserver.ast.SQLServerOutput;
import com.share.springboot.entity.ContentList;
import com.share.springboot.service.AttentionService;
import com.share.springboot.service.ContentListService;
import com.share.springboot.service.LikeListService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.GetDateUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Yu W
 * @date 2020/5/7 15:17
 */
@Controller
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentListService contentListService;
    @Autowired
    private UserListService userListService;

    @Autowired
    private LikeListService likeListService;
    @Autowired
    private AttentionService attentionService;
    //得到所有图片
    @GetMapping("/getContentAll")
    @ResponseBody
    public List<ContentList> showContent(String action,HttpSession session){
        String liker_id =(String) session.getAttribute("user_phone");
        List<ContentList> contentLists = new ArrayList<>();;
        contentLists = contentListService.selectAll();
//        for (ContentList contentList : contentLists) {
//            String user_id = contentList.getAnnouncer_id();
//            contentList.setAvatar_image(userListService.selectImage(user_id));
//            System.out.println(contentList);
//        }
        for( int i = 0 ; i < contentLists.size() ; i++){
            String content_id = contentLists.get(i).getContent_id();
            int flag = likeListService.selectExist(liker_id,content_id);
            if (flag>0){
                int a = likeListService.selectStatus(liker_id,content_id);
                contentLists.get(i).setLike_status(a);
            }else{
                contentLists.get(i).setLike_status(0);
            }
            String a = userListService.selectImage(contentLists.get(i).getAnnouncer_id());
            String b = userListService.selectUsername(contentLists.get(i).getAnnouncer_id());
            //System.out.println(a);
            contentLists.get(i).setUser_name(b);
            contentLists.get(i).setAvatar_image(a);
            //System.out.println(contentLists.get(i));
        }
            return contentLists;
    }

    /**
     * 上传图片分享
     * @param image_File
     * @return
     * @throws IOException
     */
    @RequestMapping("/postContent")
    public ModelAndView postActivity(@RequestParam("image_File") MultipartFile image_File, @RequestParam("activity_id") String activity_id, HttpSession session)throws IOException {
        //System.out.println(activity_id);
        Long time=new Date().getTime();
        String user_phone =(String)session.getAttribute("user_phone");
        String content_id = user_phone + time;
        ContentList contentList = new ContentList();
        contentList.setContent_date(GetDateUtil.getDateTime());

//        System.out.println("开始存储");
        //图片上传成功后，将图片的地址写到数据库
        //保存图片的路径（这是存在我项目中的images下了，你们可以设置路径）
//        String c =System.getProperty("user.dir");
//        String file_path = c+"\\src\\main\\resources\\static\\pictures";

        //获取原始图片的拓展名
        String originalFilename = image_File.getOriginalFilename();
        //新的文件名字

        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
//        String file_path ="/root/photo/";
        String file_path = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/pictures/";
        String path = file_path+newFileName;
        // File targetFile = new File(file_path, newFileName);
        FileUtils.copyInputStreamToFile(image_File.getInputStream(), new File(file_path, newFileName));
        //把本地文件上传到封装上传文件位置的全路径
        //InputStream inputStream = ResourceRenderer.resourceLoader(file_path);
        //file.transferTo(targetFile);

        contentList.setImage_file(newFileName);
        contentList.setContent_id(content_id);
        contentList.setAnnouncer_id(user_phone);
        contentList.setContent_activity_id(activity_id);
//

        //
        /**
         * 保存商品
         */
        contentListService.insertContent(contentList);
//        System.out.println(contentList);
        return new ModelAndView("index");
    }

    /**
     * 图片详情
     * @param content_id
     * @param model
     * @return
     */
    @RequestMapping("/toImageDetail/{content_id}")
    public String toImageDetail(@PathVariable(value = "content_id") String content_id, Model model,HttpSession session){
        String liker_id = (String)session.getAttribute("user_phone");
        ContentList contentList = new ContentList();
        contentList = contentListService.selectOne(content_id);
        int flag = likeListService.selectExist(liker_id,content_id);
        try {
            if (flag>0){
                int a = likeListService.selectStatus(liker_id,content_id);
                contentList.setLike_status(a);
            }else{
                contentList.setLike_status(0);
            }
        }catch (Exception e){
                e.printStackTrace();
        }

        int attention_status = attentionService.selectExist(liker_id,contentList.getAnnouncer_id());
        if(attention_status>0){
            int b = attentionService.selectAttention(liker_id,contentList.getAnnouncer_id());
            contentList.setAttention_status(b);
        }else{
            contentList.setAttention_status(0);
        }
        contentList.setUser_name(userListService.selectUsername(contentList.getAnnouncer_id()));
        contentList.setAvatar_image(userListService.selectImage(contentList.getAnnouncer_id()));
        //model.addAttribute("contentList",contentList);
        session.setAttribute("contentList",contentList);
//        System.out.println(contentList);
        return "pic_content";
    }

    /**
     * 得到活动下的所有图片
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toGetActivityContent")
    @ResponseBody
    public List<ContentList> toGetActivityContent(String id, Model model,HttpSession session){
        String liker_id =(String) session.getAttribute("user_phone");
        List<ContentList> contentActivities = new ArrayList<>();
        contentActivities = contentListService.selectByActivity(id);
//        for (ContentList contentList : contentLists) {
//            String user_id = contentList.getAnnouncer_id();
//            contentList.setAvatar_image(userListService.selectImage(user_id));
//            System.out.println(contentList);
//        }
        for (ContentList contentActivity : contentActivities) {
            String content_id = contentActivity.getContent_id();
            int flag = likeListService.selectExist(liker_id,content_id);
            if (flag>0){
                int a = likeListService.selectStatus(liker_id,content_id);
                contentActivity.setLike_status(a);
            }else{
                contentActivity.setLike_status(0);
            }
            String a = userListService.selectImage(contentActivity.getAnnouncer_id());
            String b = userListService.selectUsername(contentActivity.getAnnouncer_id());
            //System.out.println(a);
            contentActivity.setUser_name(b);
            contentActivity.setAvatar_image(a);
            //System.out.println(contentLists.get(i));
        }
        return contentActivities;
    }

    /**
     * 获取个人页面下的所有图片
     * @param action
     * @param session
     * @return
     */
    @RequestMapping("/getPersonContent")
    @ResponseBody
    public List<ContentList> getPersonContent(String action,HttpSession session){
        String announcer_id = (String)session.getAttribute("user_phone");
        List<ContentList> contentLists = new ArrayList<>();
        contentLists = contentListService.selectByAnn(announcer_id);
        for (ContentList contentList:contentLists){
            String content_id = contentList.getContent_id();
            int flag = likeListService.selectExist(announcer_id,content_id);
            if (flag>0){
                int a = likeListService.selectStatus(announcer_id,content_id);
                contentList.setLike_status(a);
            }else{
                contentList.setLike_status(0);
            }
        }
        return  contentLists;
    }

    @RequestMapping("/getElseContent")
    @ResponseBody
    public List<ContentList> getElseContent(HttpSession session){
        String announcer_id = (String)session.getAttribute("user_phone");
        String user_id = (String)session.getAttribute("else_user");
        List<ContentList> contentLists = new ArrayList<>();
        contentLists = contentListService.selectByAnn(user_id);
        for (ContentList contentList:contentLists){
            String content_id = contentList.getContent_id();
            int flag = likeListService.selectExist(announcer_id,content_id);
            if (flag>0){
                int a = likeListService.selectStatus(announcer_id,content_id);
                contentList.setLike_status(a);
            }else{
                contentList.setLike_status(0);
            }
        }
        return  contentLists;
    }

    @RequestMapping("/deleteImg")
    @ResponseBody
    public Map<String,String> deleteImg(String content_id){
        int count = contentListService.deleteContent(content_id);
        Map<String,String> map = new HashMap<>();
        if(count>0){
            map.put("result","success");

        }else{
            map.put("result","error");
        }
        return map;
    }


}
