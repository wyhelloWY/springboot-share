package com.share.springboot.controller;

import com.share.springboot.entity.AttentionList;
import com.share.springboot.service.AttentionService;
import com.share.springboot.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yu W
 * @date 2020/5/15 10:53
 */
@Controller
@RequestMapping("/attention")
public class AttentionController {

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private UserListService userListService;
    /**
     * 取消关注
     * @param followed_id
     * @return
     */
    @RequestMapping("/deleteAttention")
    @ResponseBody
    public Map<String,String> deleteAttention(String followed_id,HttpSession session){
        String attention_id = (String)session.getAttribute("user_phone");
        Map<String,String> map = new HashMap<>();
        int b = attentionService.updateAttentionTwo(attention_id,followed_id);
        if(b>0){
            map.put("result","success");
        }else{
            map.put("result","error");
        }
        return map;
    }

    @RequestMapping("/addAttention")
    @ResponseBody
    public Map<String,String> addAttention(String followed_id,HttpSession session){
        String attention_id = (String)session.getAttribute("user_phone");
        AttentionList attentionList = new AttentionList();
        attentionList.setAttention_id(attention_id);
        attentionList.setFollowed_id(followed_id);
        attentionList.setAttention_status(1);
        Map<String,String> map = new HashMap<>();
        int flag = attentionService.selectExist(attention_id,followed_id);
        //如果flag大于0 则说明表中已经存在数据直接修改
        if(flag>0){
            int b = attentionService.updateAttentionOne(attention_id,followed_id);
            if(b>0){
                map.put("result","success");
            }else{
                map.put("result","error");
            }
        }else{//其他结果说明表中无数据 进行插入
            attentionService.insertAttention(attentionList);
            map.put("result","success");
        }

        return map;
    }

    /**
     * 查找当前被关注用户的粉丝
     * @param session
     * @return
     */
    @RequestMapping("/selectAllAttention")
    @ResponseBody
    public List<AttentionList> selectAllAttention(HttpSession session){
        String user_id = (String)session.getAttribute("user_phone");
        List<AttentionList> attentionLists = attentionService.selectAllMyAttention(user_id);
        for (AttentionList attentionList : attentionLists){
            attentionList.setUser_name(userListService.selectUsername(attentionList.getFollowed_id()));
            attentionList.setAvatar_image(userListService.selectImage(attentionList.getFollowed_id()));
        }
        return attentionLists;
    }
}
