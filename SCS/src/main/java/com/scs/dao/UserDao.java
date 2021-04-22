package com.scs.dao;

import com.scs.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDao")
public interface UserDao {
    @Insert("insert into userinfo (username,password) values (#{username},#{password})")
    void Save(User user);

    @Select("select * from userinfo where userName=#{userName}")
    List<User> FindByName(String username);


}
