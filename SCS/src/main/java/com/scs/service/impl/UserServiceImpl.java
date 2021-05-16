package com.scs.service.impl;

import com.scs.dao.UserDao;
import com.scs.pojo.User;
import com.scs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public int Save(User user) {
        return userDao.Save(user);
    }
    @Override
    public List<User> FindByName(String username) {
        List<User> users = userDao.FindByName(username);
        return users;
    }

    @Override
    public int updatePassword(String username,String newPassword) {
        return userDao.updatePassword(username,newPassword);
    }

    @Override
    public String findPassword(String username) {
        return userDao.findPassword(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getRoleAccounts(String role) {
        return userDao.getRoleAccounts(role);
    }

    @Override
    public int updateStatus(String username, String status) {
        return userDao.updateStatus(username,status);
    }

    @Override
    public int updateAccount(User user) {
        return userDao.updateAccount(user);
    }

    @Override
    public int removeAccount(String userName) {
        return userDao.removeAccount(userName);
    }
}
