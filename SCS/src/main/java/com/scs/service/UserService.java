package com.scs.service;

import com.scs.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 向数据库存入新注册的用户
     * @param user
     */
    void Save(User user);
    /**
     * 查找该用户
     * @param username
     * @return
     */
    List<User> FindByName(String username);

}
