package com.scs.service;

import com.scs.pojo.StudentLeave;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentLeaveService {
    int insertInfo(StudentLeave studentLeave);

    int updateStatus(String rejectInfo,Integer status,Integer leaveId);

    List<StudentLeave> getLeaveOfStu(Integer status, String counselorId);
}
