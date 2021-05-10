package com.scs.service.impl;

import com.scs.dao.resourceMapper;
import com.scs.pojo.resource;
import com.scs.service.resourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class resourceServiceImpl implements resourceService {


    @Autowired
    private resourceMapper resMapper;
    @Override
    public int saveRes(resource res) {
        return resMapper.saveRes(res);
    }

    @Override
    public resource getResInfo(String fileName) {
        return resMapper.getResInfoById(fileName);
    }

    @Override
    public int deleteRes(String fileName) {
        return resMapper.deleteRes(fileName);
    }

    @Override
    public int updateRes(String filepath, String filename) {
        return resMapper.updateRes(filepath,filename);
    }
}
