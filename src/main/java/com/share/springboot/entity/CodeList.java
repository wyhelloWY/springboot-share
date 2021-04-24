package com.share.springboot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 邀请码实体类
 * @author Yu W
 * @date 2020/5/6 10:23
 */
@Data
public class CodeList implements Serializable {
    private Integer id;
    private String user_id;
    private String invitation_code;
}
