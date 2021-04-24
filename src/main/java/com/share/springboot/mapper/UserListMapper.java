package com.share.springboot.mapper;

import com.share.springboot.entity.UserList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 15:02
 */
@Mapper
public interface UserListMapper {
    /**
     * 查找用户 用于登录匹配
     * @return
     */
    UserList selectByPhone(String user_id);

    /**
     *查找所有用户的信息
     * @return
     */
    List<UserList> selectAll();

    /**
     *注册 插入用户
     * @param userList
     */
    boolean insertAll(UserList userList);

    /**
     * 更新用户的信息
     */
    void updateOne(UserList userList);

    /**
     * 修改密码
     */

    boolean updatePassword(String user_id,String user_password);

    /**
     * 搜索头像
     * @param user_id
     * @return
     */
    String selectImage(String user_id);

    /**
     * 搜索昵称
     * @param user_id
     * @return
     */
    String selectUsername(String user_id);
}
