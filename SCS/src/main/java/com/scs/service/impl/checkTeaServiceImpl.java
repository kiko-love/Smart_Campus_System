package com.scs.service.impl;

import com.scs.dao.checkTeaMapper;
import com.scs.pojo.checkTeacher;
import com.scs.service.checkTeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("checkTeaService")
public class checkTeaServiceImpl implements checkTeaService {

    @Autowired
    private checkTeaMapper checkMap;

    @Override
    public int insertCheck(checkTeacher checkTea) {
        System.out.println(checkMap);
        return checkMap.insertCheck(checkTea);
    }

    @Override
    public int updateType(checkTeacher checkTea) {
        return checkMap.updateType(checkTea);
    }

    @Override
    public List<checkTeacher> selectById(String teacherId) {
        return checkMap.selectById(teacherId);
    }

    @Override
    public List<checkTeacher> selectAll() {
        return checkMap.selectAll();
    }

}
