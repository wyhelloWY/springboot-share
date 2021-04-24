package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动内容发布表
 * @author Yu W
 * @date 2020/5/6 14:01
 */
@Data
public class ActivityList implements Serializable {
    private Integer id;
    private String activity_id;
    private String image_file;
    private String activity_image;
    private String activity_name;
    private String activity_organizer_id;
    //参加人数
    private Integer activity_participate;
    private Date activity_date;
    private String activity_content;
    @Transient
    private String user_name;

}
