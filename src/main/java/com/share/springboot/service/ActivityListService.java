package com.share.springboot.service;

import com.share.springboot.entity.ActivityList;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/8 20:19
 */
public interface ActivityListService {
    /**
     * 查找全部
     * @return
     */
    List<ActivityList> selectAllContent();

    /**
     * 查找发布此项活动的人
     * @param activity_organizer_id
     * @return
     */
    List<ActivityList> selectByUser(String activity_organizer_id);

    /**
     * 根据相应人数查询出前四个
     * @return
     */
    List<ActivityList> selectAllContentPeople();
    /**
     * 根据id搜索
     * @param activity_id
     * @return
     */
    ActivityList selectById(String activity_id);

    /**
     * 插入
     * @param activityList
     * @return
     */
    int insertOne(ActivityList activityList);

    /**
     * 删除
     * @param activity_id
     * @return
     */
    int deleteOne(String activity_id);

    //int updateOne();
}
