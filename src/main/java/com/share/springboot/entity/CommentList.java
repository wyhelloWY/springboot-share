package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论表
 * @author Yu W
 * @date 2020/5/6 10:19
 */
@Data
public class CommentList implements Serializable {
    private Integer id;
    //评论id
    private String comment_id;
    //评论人的id
    private String comment_user_id;
    //评论的内容
    private String comment_content;
    //评论的图片id
    private String comment_content_id;
    private Date comment_date;
    private String parent_comment_id;

    private List<CommentList> replyComments = new ArrayList<>();
    private CommentList parentComment;
    private String parentID;
    private String parentUsername;
    @Transient
    private String username;
    @Transient
    private String image_file;

}
