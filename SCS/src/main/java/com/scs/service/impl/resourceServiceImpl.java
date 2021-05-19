package com.scs.service.impl;

import com.scs.dao.resourceMapper;
import com.scs.pojo.resource;
import com.scs.service.resourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("resourceService")
public class resourceServiceImpl implements resourceService {


    @Autowired
    private resourceMapper resMapper;
    @Override
    public int saveRes(resource res) {
        return resMapper.saveRes(res);
    }

    @Override
    public resource getResInfoById(String fileName, String course, String teacherId) {
        return resMapper.getResInfoById(fileName,course,teacherId);
    }

    @Override
    public int deleteRes(String fileName, String course) {
        return resMapper.deleteRes(fileName,course);
    }

    @Override
    public int updateRes(String filepath, String filename,String course) {
        return resMapper.updateRes(filepath,filename,course);
    }

    @Override
    public List<String> getTeacherId(String course) {
        return resMapper.getTeacherId(course);
    }

    @Override
    public List<String> selectCourse() {
        return resMapper.selectCourse();
    }

    @Override
    public List<resource> getResInfo(String teacherId, String course) {
        return resMapper.getResInfo(teacherId,course);
    }
}
