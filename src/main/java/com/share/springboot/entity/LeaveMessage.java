package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言实体类
 * @author Yu W
 * @date 2020/5/6 10:09
 */
@Data
public class LeaveMessage implements Serializable {
    private Integer id;
    private String user_id;
    private String message_user_id;
    private String message_content;
    private Date message_date;
    private String message_id;

    @Transient
    private String image_file;
    @Transient
    private String username;
}
