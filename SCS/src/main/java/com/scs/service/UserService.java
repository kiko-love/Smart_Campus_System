package com.scs.service;

import com.scs.pojo.User;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 获取所有用户信息
     * @param
     * @return List<User>
     */
    List<User> getAllUsers();

    /**
     * 获取不同角色信息的用户账号
     * @param role
     * @return List<User>
     */
    List<User> getRoleAccounts(String role);

    /**
     * 更改账号状态
     *
     * @param username
     * @param status
     * @return List<User>
     */
    int updateStatus(String username, @Param(value = "status") String status);
}
