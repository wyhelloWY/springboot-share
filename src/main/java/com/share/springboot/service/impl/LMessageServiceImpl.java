package com.share.springboot.service.impl;

import com.share.springboot.entity.LeaveMessage;
import com.share.springboot.mapper.LeaveMessageMapper;
import com.share.springboot.service.LMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/12 14:31
 */
@Service
@Transactional
public class LMessageServiceImpl implements LMessageService {
    @Autowired
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LeaveMessageMapper leaveMessageMapper;
    @Override
    public List<LeaveMessage> selectById(String message_user_id) {
        List<LeaveMessage> list = leaveMessageMapper.selectById(message_user_id);
        return list;
    }

    @Override
    public int insertLeaveMessage(LeaveMessage leaveMessage) {
        return leaveMessageMapper.insertLeaveMessage(leaveMessage);
    }
}
