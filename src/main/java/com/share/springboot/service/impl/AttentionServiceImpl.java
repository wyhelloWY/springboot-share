package com.share.springboot.service.impl;

import com.share.springboot.entity.ActivityList;
import com.share.springboot.entity.AttentionList;
import com.share.springboot.mapper.AttentionListMapper;
import com.share.springboot.service.AttentionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/15 10:17
 */
@Service
@Transactional
public class AttentionServiceImpl implements AttentionService {
    @Autowired
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AttentionListMapper attentionListMapper;

    /**
     * 查询自己的关注
     * @param attention_id
     * @return
     */
    @Override
    public List<AttentionList> selectAllMyAttention(String attention_id) {
            List<AttentionList> lists = attentionListMapper.selectAllMyAttention(attention_id);
            return lists;
    }

    /**
     * 查询他人的关注
     * @param followed_id
     * @return
     */
    @Override
    public List<AttentionList> selectAllElseAttention(String followed_id) {
            List<AttentionList> lists = attentionListMapper.selectAllElseAttention(followed_id);
            return lists;
    }

    @Override
    public int insertAttention(AttentionList attentionList) {
        return attentionListMapper.insertAttention(attentionList);
    }

    @Override
    public int updateAttentionOne(String attention_id, String followed_id) {
        return attentionListMapper.updateAttentionOne(attention_id,followed_id);
    }

    @Override
    public int updateAttentionTwo(String attention_id, String followed_id) {
        return attentionListMapper.updateAttentionTwo(attention_id,followed_id);
    }

    @Override
    public int selectAttention(String attention_id, String followed_id) {

            return attentionListMapper.selectAttention(attention_id,followed_id);

    }

    @Override
    public int selectExist(String attention_id, String followed_id) {
        return attentionListMapper.selectExist(attention_id,followed_id);
    }
}
