package com.scs.service.impl;

import com.scs.dao.relationMapper;
import com.scs.pojo.relation;
import com.scs.service.relationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "relationService")
public class relationServiceImpl implements relationService {
    @Autowired
    private relationMapper relationMapper;
    @Override
    public int relationInsert(relation item) {
        return relationMapper.relationInsert(item);
    }

    @Override
    public List<relation> getAllRelation() {
        return relationMapper.getAllRelation();
    }
    @Override
    public int batchDeleteRelation(List<String> list) {
        return relationMapper.batchDeleteRelation(list);
    }

    @Override
    public List<relation> selectRelationByTeacherId(String teacherId) {
        return relationMapper.selectRelationByTeacherId(teacherId);
    }
}
