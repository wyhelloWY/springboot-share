package com.share.springboot.controller;

import com.share.springboot.entity.LeaveMessage;
import com.share.springboot.service.LMessageService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.GetDateUtil;
import com.share.springboot.utils.GetLongTime;
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
 * @date 2020/5/12 14:44
 */
@Controller
@RequestMapping("/leaveMessage")
public class LMessageController {

    @Autowired
    private LMessageService lMessageService;

    @Autowired
    private UserListService userListService;
    /**
     * 查看自己留言页面上的信息
     * @param
     * @return
     */
    @RequestMapping("/getMyMessage")
    @ResponseBody
    public List<LeaveMessage> getMyMessage(HttpSession session){
        List<LeaveMessage> leaveMessages = new ArrayList<>();
        String message_user_id = (String) session.getAttribute("user_phone");
        leaveMessages = lMessageService.selectById(message_user_id);
        for (LeaveMessage leaveMessage : leaveMessages){
            leaveMessage.setImage_file(userListService.selectImage(leaveMessage.getUser_id()));
            leaveMessage.setUsername(userListService.selectUsername(leaveMessage.getUser_id()));
        }
        return leaveMessages;
    }

    /**
     * 查看他人留言板
     * @param
     * @return
     */
    @RequestMapping("/getOtherMessage")
    @ResponseBody
    public List<LeaveMessage> getOtherMessage(HttpSession session){
        List<LeaveMessage> leaveMessages = new ArrayList<>();
        String message_user_id = (String) session.getAttribute("else_user");
        leaveMessages = lMessageService.selectById(message_user_id);
        for (LeaveMessage leaveMessage : leaveMessages){
            leaveMessage.setImage_file(userListService.selectImage(leaveMessage.getUser_id()));
            leaveMessage.setUsername(userListService.selectUsername(leaveMessage.getUser_id()));
        }
        return leaveMessages;
    }

    @RequestMapping("/insertLeaveMessage")
    @ResponseBody
    public Map<String,String> insertLeaveMessage(String message_content, HttpSession session){
        Map<String,String> map = new HashMap<>();
        String userphone = (String) session.getAttribute("user_phone");
        String message_user_id = (String) session.getAttribute("else_user");
        String message_id = userphone + GetLongTime.getLongStringTime();
        LeaveMessage leaveMessage = new LeaveMessage();
        leaveMessage.setMessage_date(GetDateUtil.getDateTime());
        leaveMessage.setMessage_id(message_id);
        leaveMessage.setMessage_user_id(message_user_id);
        leaveMessage.setMessage_content(message_content);
        leaveMessage.setUser_id(userphone);
        int index = lMessageService.insertLeaveMessage(leaveMessage);
        if (index>0){
            map.put("success","success");
        }else{
            map.put("error","error");
        }
        return map;
    }
}
