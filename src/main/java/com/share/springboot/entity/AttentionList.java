package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * g关注实体类
 * @author Yu W
 * @date 2020/5/6 13:59
 */
@Data
public class AttentionList implements Serializable {
    private Integer id;
    private String attention_id;
    private String followed_id;
    private Integer attention_status;
    @Transient
    protected String avatar_image;
    @Transient
    private String user_name;
}
