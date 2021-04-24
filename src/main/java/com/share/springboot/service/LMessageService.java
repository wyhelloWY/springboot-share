package com.share.springboot.service;

import com.share.springboot.entity.LeaveMessage;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/12 14:31
 */
public interface LMessageService {
    /**
     * 查找当前用户id的留言
     * @param message_user_id
     * @return
     */
    List<LeaveMessage> selectById(String message_user_id);

    /**
     * 插入留言
     * @param leaveMessage
     * @return
     */
    int insertLeaveMessage(LeaveMessage leaveMessage);
}
