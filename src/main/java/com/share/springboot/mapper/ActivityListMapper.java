package com.share.springboot.mapper;

import com.share.springboot.entity.ActivityList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:07
 */
@Mapper
public interface ActivityListMapper {

    /**
     * 查找全部
     * @return
     */
    List<ActivityList> selectAllContentTime();


    /**
     * 查找全部
     * @return
     */
    List<ActivityList> selectAllContentPeople();

    /**
     * 查找发布此项活动的人
     * @param activity_organizer_id
     * @return
     */
    List<ActivityList> selectByUser(String activity_organizer_id);

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
