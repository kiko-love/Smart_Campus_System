package com.scs.service;

import com.scs.pojo.relation;

import java.util.List;

public interface relationService {
    int relationInsert(relation item);
    List<relation> getAllRelation();
    int batchDeleteRelation(List<String> list);
    List<relation> selectRelationByTeacherId(String teacherId);
}
