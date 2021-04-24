package com.share.springboot.mapper;

import com.share.springboot.entity.LikeList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:03
 */
@Mapper
public interface LikeListMapper {
    /**
     * 根据内容id查找点赞用户
     * @param like_content_id
     * @return
     */
    List<LikeList> selectAll(String like_content_id);

    /**
     * 查询点赞状态
     * @param liker_id
     * @param like_content_id
     * @return
     */
    int selectStatus(String liker_id,String like_content_id);

    /**
     * 查询是否存在
     * @param liker_id
     * @param like_content_id
     * @return
     */
    int selectExist(String liker_id,String like_content_id);
    /**
     * 对内容进行点赞
     * @param likeList
     * @return
     */
    int insertOne(LikeList likeList);

    /**
     *对内容取消赞
     * @param liker_id
     * @param like_content_id
     * @return
     */
    int updateOne(String liker_id,String like_content_id);

    int updateTwo(String liker_id,String like_content_id);

}
