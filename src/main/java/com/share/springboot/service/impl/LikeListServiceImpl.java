package com.share.springboot.service.impl;

import com.share.springboot.entity.LikeList;
import com.share.springboot.mapper.LikeListMapper;
import com.share.springboot.service.LikeListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/13 21:28
 */
@Service
@Transactional
public class LikeListServiceImpl implements LikeListService {
    @Autowired
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LikeListMapper likeListMapper;
    @Override
    public List<LikeList> selectAll(String like_content_id) {
        return likeListMapper.selectAll(like_content_id);
    }

    @Override
    public int insertOne(LikeList likeList) {
        return likeListMapper.insertOne(likeList);
    }

    @Override
    public int selectStatus(String liker_id, String like_content_id) {
        return likeListMapper.selectStatus(liker_id,like_content_id);
    }

    @Override
    public int selectExist(String liker_id, String like_content_id) {
        return likeListMapper.selectExist(liker_id,like_content_id);
    }

    @Override
    public int updateOne(String liker_id, String like_content_id) {
        return likeListMapper.updateOne(liker_id,like_content_id);
    }

    @Override
    public int updateTwo(String liker_id, String like_content_id) {
        return likeListMapper.updateTwo(liker_id,like_content_id);
    }
}
