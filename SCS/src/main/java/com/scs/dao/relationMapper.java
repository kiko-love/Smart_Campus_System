package com.scs.dao;

import com.scs.pojo.relation;

import java.util.List;

public interface relationMapper {
    int relationInsert(relation item);
    List<relation> getAllRelation();
    int batchDeleteRelation(List<String> list);
    List<relation> selectRelationByTeacherId(String teacherId);
}
