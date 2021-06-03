package com.scs.service;

import com.scs.pojo.classInfo;
import org.springframework.cglib.core.ClassInfo;

import java.util.List;

public interface ClassInfoService {
    List<ClassInfo> getClassInfo();
    int insertClassInfo(classInfo classInfo);
    int updateClassInfo(classInfo classInfo);
    int deleteClassInfo(String classId);
    int batchDeleteClassInfo(List<String> list);
    classInfo getInfo(String classes,String Major);
}
