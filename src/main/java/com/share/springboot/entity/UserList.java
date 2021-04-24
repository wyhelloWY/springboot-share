package com.share.springboot.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 用户实体类
 * @author Yu W
 * @date 2020/5/6 9:55
 */
@Data
public class UserList implements Serializable {

    private Integer id;

    private String user_id;

    private String user_name;

    private String avatar_image;

    private String usr_avatar;

    private Integer user_age;

    private String user_school;

    private String user_password;

    private String user_motto;

    private String user_type;

    private String user_code;

    @Transient
    private Integer status;



}
