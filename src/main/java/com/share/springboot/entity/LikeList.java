package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 点赞实体类
 * @author Yu W
 * @date 2020/5/6 10:07
 */
@Data
public class LikeList implements Serializable {
    private Integer id;
    //点赞人
    private String liker_id;
    //被点赞人
    private String liked_id;
    //被点赞人内容
    private String like_content_id;
    //点赞状态
    private int like_status;

    @Transient
    private String username;


}
