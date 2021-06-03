package com.scs.service.impl;

import com.scs.dao.StudentLeaveMapper;
import com.scs.pojo.StudentLeave;
import com.scs.service.StudentLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StudentLeaveService")
public class StudentLeaveServiceImpl implements StudentLeaveService {
    @Autowired
    private StudentLeaveMapper studentLeaveMapper;
    @Override
    public int insertInfo(StudentLeave studentLeave) {
        return studentLeaveMapper.insertInfo(studentLeave);
    }

    @Override
    public int updateStatus(String rejectInfo, Integer status, Integer leaveId) {
        return studentLeaveMapper.updateStatus(rejectInfo,status,leaveId);
    }

    @Override
    public List<StudentLeave> getLeaveOfStu(Integer status, String counselorId) {
        return studentLeaveMapper.getLeaveOfStu(status,counselorId);
    }


}
