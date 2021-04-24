package com.share.springboot.service.impl;

import com.share.springboot.entity.CommentList;
import com.share.springboot.mapper.CommentListMapper;
import com.share.springboot.service.CommentListService;
import com.share.springboot.service.UserListService;
import com.share.springboot.utils.GetDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/15 13:33
 */
@Service
@Transactional
public class CommentListServiceImpl implements CommentListService {
    @Autowired
    private CommentListMapper commentListMapper;

    @Autowired
    private UserListService userListService;

    //存放迭代找出的所有子代的集合
    private List<CommentList> tempReplys = new ArrayList<>();

    @Override
    public List<CommentList> listComment(String comment_content_id) {
        //查询出父节点
        List<CommentList> comments = commentListMapper.findByParentIdNull("-1",comment_content_id);
        for(CommentList comment : comments){
            String id = comment.getComment_id();
            String parent_userID = comment.getComment_user_id();
//            System.out.println("parent_userID"+parent_userID);
            //注入头像和昵称
            comment.setUsername(userListService.selectUsername(comment.getComment_user_id()));
            comment.setImage_file(userListService.selectImage(comment.getComment_user_id()));
            List<CommentList> childComments = commentListMapper.findByParentIdNotNull(id,comment_content_id);
            //查询出子评论
            combineChildren(childComments, parent_userID,comment_content_id);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(List<CommentList> childComments, String parent_userID,String comment_content_id) {
        //判断是否有一级子回复
        if (childComments.size() > 0) {
            //循环找出子评论的id
            for (CommentList childComment : childComments) {
                String childId = childComment.getComment_id();
                String parentID = childComment.getComment_id();
                String user_id = childComment.getComment_user_id();
                childComment.setParentID(parent_userID);
                childComment.setParentUsername(userListService.selectUsername(parent_userID));
                childComment.setUsername(userListService.selectUsername(user_id));
                childComment.setImage_file(userListService.selectImage(user_id));
//                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);

                //查询二级以及所有子集回复
                recursively(childId, parentID,comment_content_id,user_id);
            }
        }
    }
    private void recursively(String childId, String parentID,String comment_content_id,String user_id) {
        //根据子一级评论的id找到子二级评论
        List<CommentList> replayComments = commentListMapper.findByReplayId(childId,comment_content_id);

        if(replayComments.size() > 0){
            for(CommentList replayComment : replayComments){
                String parent_id = replayComment.getComment_id();
                String userid = replayComment.getComment_user_id();
                replayComment.setParentUsername(userListService.selectUsername(user_id));
                replayComment.setUsername(userListService.selectUsername(userid));
                replayComment.setImage_file(userListService.selectImage(userid));
                replayComment.setParentID(parentID);
                String replayId = replayComment.getComment_id();
                tempReplys.add(replayComment);
                //循环迭代找出子集回复
                recursively(replayId,parent_id,comment_content_id,user_id);
            }
        }
    }
    @Override
    public int saveComment(CommentList commentList) {
        commentList.setComment_date(GetDateUtil.getDateTime());
        return commentListMapper.saveComment(commentList);
    }
}
