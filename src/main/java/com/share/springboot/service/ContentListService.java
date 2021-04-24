package com.share.springboot.service;

import com.share.springboot.entity.ContentList;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/7 12:39
 */

public interface ContentListService {
    /**
     * 查找内容
     * @param content_id
     * @return
     */
    ContentList selectOne(String content_id);

    /**
     * 查找全部
     * @return
     */
    List<ContentList> selectAll();

    /**
     * 插入内容
     * @param contentList
     */
    int insertContent(ContentList contentList);

    /**
     * 修改内容
     */
    int updateContent(ContentList contentList);

    /**
     * 删除内容
     * @param content_id
     */
    int deleteContent(String content_id);

    /**
     * 查找同一个id发布的所有内容
     * @param announcer_id
     * @return
     */
    List<ContentList> selectByAnn(String announcer_id);

    List<ContentList> selectByActivity(String content_activity_id);
    /**
     * 查找当前内容点赞数
     * @param content_id
     * @return
     */
    int selectLike(String content_id);

    /**
     * 修改点赞数
     * @param content_id
     * @return
     */
    int updateLike(String content_id);
    /**
     * 减少点赞数
     * @param content_id
     * @return
     */
    int updateLikeReduce(String content_id);
    int updateLikeList(String content_id,Integer content_like);
}
