package com.scs.service;

import com.scs.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 向数据库存入新注册的用户
     * @param user
     */
    int Save(User user);
    /**
     * 查找该用户
     * @param username
     * @return
     */
    List<User> FindByName(String username);

    /**
     * 修改用户密码
     * @param username
     * @param newPassword
     * @return int
     */
    int updatePassword(String username,String newPassword);

    /**
     * 查找用户密码
     * @param username
     * @return int
     */
    String findPassword(String username);
}
