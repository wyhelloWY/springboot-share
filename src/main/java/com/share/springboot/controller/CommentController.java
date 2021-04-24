package com.share.springboot.controller;

import com.share.springboot.entity.CommentList;
import com.share.springboot.service.CommentListService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.GetLongTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/15 14:15
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentListService commentListService;

    @Autowired
    private UserListService userListService;
    @GetMapping("/AllComment/{content_id}")
    public String comments(@PathVariable("content_id") String comment_content_id, Model model) {
//        System.out.println("进入");
        List<CommentList> comments = commentListService.listComment(comment_content_id);
        model.addAttribute("comments", comments);
//        for (CommentList commentList:comments){
//            System.out.println(commentList);
//        }

        return "pic_content :: allCommentList";
    }

    @PostMapping("/getAllComment")
    public String getAllComment(CommentList commentList, HttpSession session) {
        //设置头像

        String user_id =(String) session.getAttribute("user_phone");
        String comment_id = user_id + GetLongTime.getLongStringTime();
        commentList.setComment_id(comment_id);
        commentList.setComment_user_id(user_id);
//        commentList.setImage_file(userListService.selectImage(user_id));
//        commentList.setUsername(userListService.selectUsername(user_id));
//        System.out.println(commentList);
        if (commentList.getParentComment().getComment_id() != null) {
            commentList.setParent_comment_id(commentList.getParentComment().getComment_id());
        }
        commentListService.saveComment(commentList);
        return "pic_content";
    }
}
