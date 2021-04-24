package com.share.springboot.service;

import com.share.springboot.entity.CommentList;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/15 13:33
 */
public interface CommentListService {
    //查询评论列表
    List<CommentList> listComment(String comment_content_id);

    //保存评论
    int saveComment(CommentList commentList);
}
