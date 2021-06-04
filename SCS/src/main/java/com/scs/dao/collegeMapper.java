package com.scs.dao;

import com.scs.pojo.college;
import java.util.List;

public interface collegeMapper {
    /**
     * 插入学院信息
     * @param college
     * @return
     */
    int addCollege(college college);

    /**
     * 删除某个学院的信息
     * @param collegeName
     * @return
     */
    int deleteCollege(String collegeName);

    /**
     * 更新学院信息
     * @param college
     * @return
     */
    int updateCollege(college college);


    //获取所有学院列表
    List<college> selectAllCollege();

    //根据Id查询学院列表
    List<college> selectCollegeById(String collegeId);

    //根据关键词查询学院(模糊查询)
    List<college> selectCollege(String key);


    //批量删除学院信息
    int batchRemoveCollege(List<String> List);
}
