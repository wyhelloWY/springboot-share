package com.share.springboot.entity;

import lombok.Data;

//import javax.persistence.Transient;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * 活动内容
 * @author Yu W
 * @date 2020/5/6 10:13
 */
@Data
public class ContentList implements Serializable {

    private Integer id;
    private String content_id;
    private String announcer_id;
    private String image_file;
    //图片名字
    private String content_image;
    private Date content_date;
    private Integer content_like;
    private Integer content_comment;
    private Integer content_share;
    private String content_activity_id;
    @Transient
    protected String avatar_image;
    @Transient
    private String user_name;
    @Transient
    private Integer like_status;
    @Transient
    private Integer attention_status;
}
