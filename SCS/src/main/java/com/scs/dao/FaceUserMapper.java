package com.scs.dao;

import com.scs.pojo.FaceUser;

import java.util.List;

public interface FaceUserMapper {
    //获取全部用户列表
    List<FaceUser> getUserList();
    //根据id获取用户信息
    FaceUser getUserById(String id);
    //增加用户信息
    int addUser(FaceUser user);
    //修改用户信息
    int updateUser(FaceUser user);
    //根据id删除用户
    int deleteUser(String userid);
}
