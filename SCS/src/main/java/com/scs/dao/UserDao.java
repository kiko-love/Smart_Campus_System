package com.scs.dao;

import com.scs.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDao")
public interface UserDao {

    //插入新用户
    @Insert("insert into userinfo (username,password) values (#{username},#{password})")
    int Save(User user);

    //获取指定用户信息
    @Select("select * from userinfo where userName=#{userName}")
    List<User> FindByName(String username);

    //获取全部用户信息
    @Select("select * from userinfo")
    List<User> getAllUsers();

    //获取不同角色信息的用户账号
    List<User> getRoleAccounts(@Param(value = "role") String role);


    //更改账号角色
    int updateAccount(User user);

    //删除指定账号信息
    int removeAccount(String userName);

    //批量删除账号信息
    int batchRemoveAccount(List<String> List);

    //更改账号状态
    int updateStatus(@Param(value = "username")String username,@Param(value = "status") String status);


    //修改密码
    @Update("update userinfo set md5password = #{md5password} where username = #{username}")
    int updatePassword(@Param(value = "username")String username, @Param(value = "md5password")String newPassword);
    //查询用户的密码
    @Select("select md5password from userinfo where username = #{username}")
    String findPassword(String username);



}
