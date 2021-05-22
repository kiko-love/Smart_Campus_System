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
    public resource getResInfoById(String fileName, String courseName, String teacherId) {
        return resMapper.getResInfoById(fileName,courseName,teacherId);
    }

    @Override
    public int deleteRes(String fileName, String courseName) {
        return resMapper.deleteRes(fileName,courseName);
    }

    @Override
    public int updateRes(String filepath, String filename,String courseName) {
        return resMapper.updateRes(filepath,filename,courseName);
    }

    @Override
    public List<String> getTeacherId(String courseName) {
        return resMapper.getTeacherId(courseName);
    }

    @Override
    public List<String> selectcourseName() {
        return resMapper.selectcourseName();
    }

    @Override
    public List<resource> getResInfo(String teacherId, String courseName) {
        return resMapper.getResInfo(teacherId,courseName);
    }

    @Override
    public List<String> getCourseByTeacherId(String teacherId) {
        return resMapper.getCourseByTeacherId(teacherId);
    }

    @Override
    public List<resource> getResourceByCourse(String courseName) {
        return resMapper.getResourceByCourse(courseName);
    }
}
