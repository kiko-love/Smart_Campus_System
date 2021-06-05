package com.scs.service;

import com.scs.pojo.LogOB;
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
     * @return int
     */
    int updateStatus(String username, @Param(value = "status") String status);


    /**
     * 更改账号状态
     *
     * @param user
     * @return int
     */
    int updateAccount(User user);

    /**
     * 删除指定账号信息
     *
     * @param userName
     * @return int
     */
    int removeAccount(String userName);


    /**
     * 增加账号信息
     *
     * @param user
     * @return int
     */
    int addAccount(User user);

    /**
     * 批量删除账号
     *
     * @param List
     * @return int
     */
    int batchRemoveAccount(List<String> List);

    /**
     * 查询账号信息
     *
     * @param username
     * @return List<User>
     */
    List<User> searchAccount(@Param(value = "username")String username);

    /**
     * 插入登陆日志记录
     *
     * @param logOB
     * @return int
     */
    int addLogRecord(LogOB logOB);


    /**
     * 获取登录日志
     *  @return List<LogOB>
     */
    List<LogOB> getLogRecords();

}
