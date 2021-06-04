package com.scs.service;

import com.scs.pojo.major;

import java.util.List;

public interface majorService {
    /**
     * 插入专业信息
     * @param major
     * @return
     */
    int addMajor(major major);
    /**
     * 删除某个专业的信息
     * @param majorName
     * @return
     */
    int deleteMajor(String majorName);

    int updateMajor(major major);

    //获取所有专业列表
    List<major> selectAllMajor();

    //根据名称查询学院ID
    List<major> selectCollegeIdByName(String collegeName);

    //根据专业ID查询专业
    List<major> selectByMajorId(String majorId);

    //查询所有学院列表
    List<major> selectAllColleges();

    int batchRemoveMajors(List<String> List);
}
