package com.scs.service;

import com.scs.pojo.TeacherOfCourseOB;
import com.scs.pojo.myResource;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface myResourceService {

    int insertMyResource(myResource myResource);

    List<String> selectMyFocusCourse(String userId);

    List<TeacherOfCourseOB> selectTeacherOfCourse(String userId, String courseName);

    List<myResource> selectOneResource(String userId,String courseName,String teacherId);

    int batchDeleteFocus(List<Integer> List);

    List<myResource> selectMyResById(String userId);
}
