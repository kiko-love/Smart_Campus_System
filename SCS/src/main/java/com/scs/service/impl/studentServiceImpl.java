package com.scs.service.impl;

import com.scs.dao.studentMapper;
import com.scs.pojo.student;
import com.scs.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("studentService")
public class studentServiceImpl implements studentService {
    @Autowired
    private studentMapper studentMapper;

    @Override
    public List<student> getStudents() {
        return studentMapper.getStudents();
    }

    @Override
    public int addStudent(student stu) {
        return studentMapper.addStudent(stu);
    }

    @Override
    public int removeStudent(String userId) {
        return studentMapper.removeStudent(userId);
    }

    @Override
    public List<student> getStudentById(String userId) {
        return studentMapper.getStudentById(userId);
    }

    @Override
    public int updateStudent(student stu) {
        return studentMapper.updateStudent(stu);
    }

    @Override
    public int batchRemove(List<String> List) {
        return studentMapper.batchRemove(List);
    }
}
