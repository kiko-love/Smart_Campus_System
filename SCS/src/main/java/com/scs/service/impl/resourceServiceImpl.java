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
    public resource getResInfoById(String fileName, String profession) {
        return resMapper.getResInfoById(fileName,profession);
    }

    @Override
    public int deleteRes(String fileName, String profession) {
        return resMapper.deleteRes(fileName,profession);
    }

    @Override
    public int updateRes(String filepath, String filename,String profession) {
        return resMapper.updateRes(filepath,filename,profession);
    }

    @Override
    public List<String> getTeacherId(String profession) {
        return resMapper.getTeacherId(profession);
    }

    @Override
    public List<String> selectProfession() {
        return resMapper.selectProfession();
    }

    @Override
    public List<resource> getResInfo(String teacherId, String profession) {
        return resMapper.getResInfo(teacherId,profession);
    }
}
