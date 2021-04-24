package com.share.springboot.service;

import com.share.springboot.entity.AttentionList;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/15 10:17
 */
public interface AttentionService {
    /**
     * 查询用户关注到的人
     * @param attention_id
     * @return
     */
    List<AttentionList> selectAllMyAttention(String attention_id);

    /**
     * 查询所有关注他的人
     * @param followed_id
     * @return
     */
    List<AttentionList> selectAllElseAttention(String followed_id);
    /**
     * 插入关注
     * @return
     */
    int insertAttention(AttentionList attentionList);

    /**
     * 修改关注状态关注
     * @param attention_id
     * @param followed_id
     * @return
     */
    int updateAttentionOne(String attention_id,String followed_id);

    /**
     * 修改关注状态不关注
     * @param attention_id
     * @param followed_id
     * @return
     */
    int updateAttentionTwo(String attention_id,String followed_id);

    /**
     * 查找关注
     * @param attention_id
     * @param followed_id
     * @return
     */
    int selectAttention(String attention_id,String followed_id);

    /**
     * 判断关注行数是否存在
     * @param attention_id
     * @param followed_id
     * @return
     */
    int selectExist(String attention_id,String followed_id);
}
