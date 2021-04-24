package com.share.springboot.service.impl;

import com.share.springboot.entity.ActivityList;
import com.share.springboot.entity.ContentList;
import com.share.springboot.mapper.ActivityListMapper;
import com.share.springboot.service.ActivityListService;
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
 * @date 2020/5/8 20:20
 */
@Service
@Transactional
public class ActivityListServiceImpl implements ActivityListService {

    @Autowired
    private ActivityListMapper activityListMapper;
    @Autowired
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 查询所有活动
     * @return
     */
    @Override
    public List<ActivityList> selectAllContent() {

            return activityListMapper.selectAllContentTime();
    }

    @Override
    public List<ActivityList> selectByUser(String activity_organizer_id) {
            return activityListMapper.selectByUser(activity_organizer_id);
    }

    @Override
    public List<ActivityList> selectAllContentPeople() {
        return activityListMapper.selectAllContentPeople();
    }

    @Override
    public ActivityList selectById(String activity_id) {
            //得到学生对象
            ActivityList activityList = activityListMapper.selectById(activity_id);
            return activityList;
    }

    @Override
    public int insertOne(ActivityList activityList) {
        int count  = activityListMapper.insertOne(activityList);
        return count;
    }

    @Override
    public int deleteOne(String activity_id) {
        return activityListMapper.deleteOne(activity_id);
    }
}
