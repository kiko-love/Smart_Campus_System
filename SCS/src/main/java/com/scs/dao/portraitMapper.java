package com.scs.dao;

import com.scs.pojo.portrait;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface portraitMapper {

    @Insert("insert into portrait (userId,role,p_name,p_path) values(#{userId},#{role},#{p_name},#{p_path})")
    int savePortrait(portrait port);

    @Select("select * from portrait where userid=#{userId}")
    int getPortraitById(String userId);
}
