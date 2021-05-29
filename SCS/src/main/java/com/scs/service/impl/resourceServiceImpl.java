package com.scs.service.impl;

import com.scs.dao.resourceMapper;
import com.scs.pojo.resource;
import com.scs.service.resourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public int deleteRes(Integer fileId) {
        return resMapper.deleteRes(fileId);
    }

    @Override
    public int updateRes(String filepath, String createTime, String fileSize, String filename, String courseName) {
        return resMapper.updateRes(filepath,createTime,fileSize,filename,courseName);
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

    @Override
    public List<resource> getResourceByTeacherId(String teacherId) {
        return resMapper.getResourceByTeacherId(teacherId);
    }

    @Override
    public List<resource> getAllResource() {
        return resMapper.getAllResource();
    }

    @Override
    public int batchDeleteResource(List<Integer> List) {
        return resMapper.batchDeleteResource(List);
    }

    @Override
    public List<resource> selectResourceById(Integer fileId) {
        return resMapper.selectResourceById(fileId);
    }

    @Override
    public List<resource> selectByFileName(String teacherId, String fileName) {
        return resMapper.selectByFileName(teacherId,fileName);
    }
}
