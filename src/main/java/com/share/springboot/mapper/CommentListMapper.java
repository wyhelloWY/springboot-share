package com.share.springboot.mapper;

import com.share.springboot.entity.CommentList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:06
 */
@Mapper
@Repository
public interface CommentListMapper {
    //添加一个评论
    int saveComment(CommentList commentList);

    //查询父级评论
    List<CommentList> findByParentIdNull(@Param("parent_comment_id") String parent_comment_id,@Param("comment_content_id")String comment_content_id);

    //查询一级回复
    List<CommentList> findByParentIdNotNull(@Param("comment_id") String comment_id,@Param("comment_content_id")String comment_content_id);

    //查询二级以及所有子集回复
    List<CommentList> findByReplayId(@Param("childId") String childId,@Param("comment_content_id")String comment_content_id);
}
