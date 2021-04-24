package com.share.springboot.mapper;

import com.share.springboot.entity.ContentList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:04
 */
@Mapper
public interface ContentListMapper {
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

    /**
     * 查找同一个id发布的所有内容
     * @param content_activity_id
     * @return
     */
    List<ContentList> selectByActivity(String content_activity_id);

    /**
     * 查找当前内容点赞数
     * @param content_id
     * @return
     */
    int selectLike(String content_id);

    /**
     * 增加点赞数
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
