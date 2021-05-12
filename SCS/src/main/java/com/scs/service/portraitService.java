package com.scs.service;

import com.scs.pojo.portrait;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


public interface portraitService {

    int savePortrait(portrait port);


    portrait getPortraitById(String userId);

    int updatePortrait(String p_name,String p_path,String userId);
}
