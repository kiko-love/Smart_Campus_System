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
}
