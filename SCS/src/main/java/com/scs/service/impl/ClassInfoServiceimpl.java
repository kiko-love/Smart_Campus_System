package com.scs.service.impl;

import com.scs.dao.ClassInfoMapper;
import com.scs.pojo.classInfo;
import com.scs.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ClassInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ClassInfoService")
public class ClassInfoServiceimpl implements ClassInfoService {
    @Autowired
    public ClassInfoMapper classInfoMapper;
    @Override
    public List<ClassInfo> getClassInfo() {
        return classInfoMapper.getClassInfo();
    }

    @Override
    public int insertClassInfo(classInfo classInfo) {
        return classInfoMapper.insertClassInfo(classInfo);
    }

    @Override
    public int updateClassInfo(classInfo classInfo) {
        return classInfoMapper.updateClassInfo(classInfo);
    }

    @Override
    public int deleteClassInfo(String classId) {
        return classInfoMapper.deleteClassInfo(classId);
    }

    @Override
    public int batchDeleteClassInfo(List<String> list) {
        return classInfoMapper.batchDeleteClassInfo(list);
    }
}
