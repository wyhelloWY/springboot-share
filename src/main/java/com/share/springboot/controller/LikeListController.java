package com.share.springboot.controller;

import com.share.springboot.common.NoRepeatSubmit;
import com.share.springboot.entity.ContentList;
import com.share.springboot.entity.LikeList;
import com.share.springboot.service.ContentListService;
import com.share.springboot.service.LikeListService;
import com.share.springboot.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu W
 * @date 2020/5/13 21:38
 */
@Controller
@RequestMapping("/likeList")
public class LikeListController {
    @Autowired
    private LikeListService likeListService;
    @Autowired
    private ContentListService contentListService;
    @Autowired
    private UserListService userListService;
    @RequestMapping("/addLike")
    @ResponseBody
    @NoRepeatSubmit
    public Map<String,String> addLike(String content_id , String content_like  ,String liked_id, HttpSession session){
        int like = Integer.valueOf(content_like).intValue();
//        System.out.println(like);
        Map<String,String> map = new HashMap<>();
        String liker_id =(String) session.getAttribute("user_phone");
        LikeList likeList = new LikeList();
        likeList.setLiker_id(liker_id);
        likeList.setLiked_id(liked_id);
        likeList.setLike_content_id(content_id);
        likeList.setLike_status(1);
        //查询点赞用户是否存在
        int flag = likeListService.selectExist(liker_id,content_id);
        //如果存在直接修改,在进行查询点赞状态是否已经点赞
        if(flag>0){
            int a = likeListService.selectStatus(liker_id,content_id);
            //a 大于0则说明用户已经点赞
            if(a>0){
                map.put("result","exist");
            }else{//其他结果用户未点赞
                //修改点赞数
                like++;
                contentListService.updateLikeList(content_id,like);
                likeListService.updateTwo(liker_id,content_id);
                map.put("result","success");
            }
            //不存在则进行插入
        }else {
            like++;
            contentListService.updateLikeList(content_id,like);
            likeListService.insertOne(likeList);
            map.put("result","success");
        }
        return map;
    }
    @RequestMapping("/reduceLike")
    @ResponseBody
    @NoRepeatSubmit
    public Map<String,String> reduceLike(String content_id , String content_like  ,String liked_id, HttpSession session){
        int like = Integer.valueOf(content_like).intValue();
        Map<String,String> map = new HashMap<>();
        String liker_id =(String) session.getAttribute("user_phone");
        LikeList likeList = new LikeList();
        likeList.setLiker_id(liker_id);
        likeList.setLiked_id(liked_id);
        likeList.setLike_content_id(content_id);
        likeList.setLike_status(0);

            int a = likeListService.selectStatus(liker_id,content_id);
            //a 大于0则说明用户已经点赞
            if(a>0){
                like--;
                contentListService.updateLikeList(content_id,like);
                likeListService.updateOne(liker_id,content_id);
                map.put("result","success");

            }else{//其他结果用户未点赞
                //修改点赞数
                map.put("result","error");

            }
            //不存在则进行插入

        return map;
    }

    @RequestMapping("/getUserLikes")
    @ResponseBody
    public List<LikeList> showLikePeople(String content_id,HttpSession session){
        List<LikeList> likeLists = likeListService.selectAll(content_id);
        if(likeLists!=null||likeLists.size()!=0){
            for (LikeList likeList:likeLists){
                likeList.setUsername(userListService.selectUsername(likeList.getLiker_id()));
            }
        }
        return likeLists;
    }
}
