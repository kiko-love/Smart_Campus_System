package com.scs.service.impl;

import com.scs.dao.FaceUserMapper;
import com.scs.pojo.FaceUser;
import com.scs.service.FaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FaceUserService")
public class FaceUserServiceImpl implements FaceUserService {
    @Autowired
    private FaceUserMapper faceUserMapper;

    //获取全部用户列表
    @Override
    public List<FaceUser> getUserList(){
        return faceUserMapper.getUserList();
    };

    @Override
    //根据id获取用户信息
    public FaceUser getUserById(String id){
        return faceUserMapper.getUserById(id);
    };

    //增加用户信息
    @Override
    public int addUser(FaceUser user){
        return faceUserMapper.addUser(user);
    };

    @Override
    //修改用户信息
    public int updateUser(FaceUser user){
        return faceUserMapper.updateUser(user);
    };

    @Override
    //根据id删除用户
    public int deleteUser(String userid) {
        return faceUserMapper.deleteUser(userid);
    }
}
