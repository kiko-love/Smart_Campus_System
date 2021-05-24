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
    List<major> selectAllMajor();
    List<major> selectByMajorName(String majorName);

    int batchRemoveMajors(List<String> List);
}
