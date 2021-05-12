package com.scs.dao;

import com.scs.pojo.portrait;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface portraitMapper {

    @Insert("insert into portrait (userId,role,p_name,p_path) values(#{userId},#{role},#{p_name},#{p_path})")
    int savePortrait(portrait port);

    @Select("select * from portrait where userid=#{userId}")
    portrait getPortraitById(String userId);

    @Update("update portrait set p_name=#{arg0},p_path=#{arg1} where userid=#{arg2}")
    int updatePortrait(String p_name,String p_path,String userId);


}
