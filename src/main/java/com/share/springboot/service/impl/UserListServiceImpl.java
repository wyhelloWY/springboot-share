package com.share.springboot.service.impl;

import com.share.springboot.entity.UserList;
import com.share.springboot.mapper.UserListMapper;
import com.share.springboot.service.UserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yu W
 * @date 2020/5/6 19:00
 */
@Service
@Transactional
public class UserListServiceImpl implements UserListService {
    @Autowired
    RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserListMapper userListMapper;

    /**
     * 查找全部
     * @param user_id
     * @return
     */
    @Override
    public UserList selectByPhone(String user_id) {
            UserList userList = userListMapper.selectByPhone(user_id);
            return userList;
    }

    @Override
    public List<UserList> selectAll() {
        return userListMapper.selectAll();
    }

    @Override
    public boolean insertAll(UserList userList) {
        boolean success = userListMapper.insertAll(userList);
        if (success == true){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void updateOne(UserList userList) {

        userListMapper.updateOne(userList);
    }

    @Override
    public boolean updatePassword(String user_id, String user_password) {
        boolean success = userListMapper.updatePassword(user_id,user_password);
        if (success == true){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 搜索头像
     * @param user_id
     * @return
     */
    public String selectImage(String user_id){
            String  value = userListMapper.selectImage(user_id);
            return  value;
    }

    /**
     * 搜索昵称
     * @param user_id
     * @return
     */
    public String selectUsername(String user_id){

            String value = userListMapper.selectUsername(user_id);
            return value;
    }
}
