package com.scs.dao;

import com.scs.pojo.classInfo;
import org.springframework.cglib.core.ClassInfo;

import java.util.List;

public interface ClassInfoMapper {
    List<ClassInfo> getClassInfo();
    int insertClassInfo(classInfo classInfo);
    int updateClassInfo(classInfo classInfo);
    int deleteClassInfo(String classId);
    int batchDeleteClassInfo(List<String> list);
}
