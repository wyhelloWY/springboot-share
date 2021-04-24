package com.share.springboot.service.impl;

import com.share.springboot.entity.ContentList;
import com.share.springboot.mapper.ContentListMapper;
import com.share.springboot.service.ContentListService;
import com.share.springboot.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/7 12:39
 */
@Service
@Transactional
public class ContentListServiceImpl implements ContentListService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ContentListMapper contentListMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    /**
     * 查询单一内容
     * @param content_id
     * @return
     */
    @Override
    public ContentList selectOne(String content_id) {
            ContentList contentList = contentListMapper.selectOne(content_id);
            return contentList;

    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<ContentList> selectAll() {
            List<ContentList> contentLists = contentListMapper.selectAll();
//            System.out.println("未有缓存");
            return contentLists;

    }

    /**
     * 插入内容
     * @param contentList
     * @return
     */
    @Override
    public int insertContent(ContentList contentList) {
        int count = contentListMapper.insertContent(contentList);
        return count;
    }

    /**
     * 更新内容
     * @param contentList
     * @return
     */
    @Override
    public int updateContent(ContentList contentList) {

        return 0;
    }

    /**
     * 删除内容
     * @param content_id
     * @return
     */
    @Override
    public int deleteContent(String content_id) {
        int count = contentListMapper.deleteContent(content_id);
        return count;
    }

    /**
     * 根据发布人查询所有信息
     * @param announcer_id
     * @return
     */
    @Override
    public List<ContentList> selectByAnn(String announcer_id) {
            List<ContentList> contentLists = contentListMapper.selectByAnn(announcer_id);
            return contentLists;
    }

    /**
     * 查询活动下的内容
     * @param content_activity_id
     * @return
     */
    @Override
    public List<ContentList> selectByActivity(String content_activity_id) {
            List<ContentList> contentLists = contentListMapper.selectByActivity(content_activity_id);
            return contentLists;
    }

    /**
     * 查询点赞
     * @param content_id
     * @return
     */
    @Override
    public int selectLike(String content_id) {

        return contentListMapper.selectLike(content_id);
    }

    /**
     * 更新点赞
     * @param content_id
     * @return
     */
    @Override
    public int updateLike(String content_id) {
        int count = contentListMapper.updateLike(content_id);
        return count;
    }

    /**
     * 点赞减少
     * @param content_id
     * @return
     */
    @Override
    public int updateLikeReduce(String content_id) {
        int count = contentListMapper.updateLikeReduce(content_id);
        return count;
    }

    /**
     * 更新点赞
     * @param content_id
     * @param content_like
     * @return
     */
    @Override
    public int updateLikeList(String content_id, Integer content_like) {
        int count = contentListMapper.updateLikeList(content_id,content_like);

        return count;
    }
}
