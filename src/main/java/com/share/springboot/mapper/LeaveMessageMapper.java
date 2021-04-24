package com.share.springboot.mapper;

import com.share.springboot.entity.LeaveMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:03
 */
@Mapper
public interface LeaveMessageMapper {
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
