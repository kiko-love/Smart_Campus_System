package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class courseTeaOfFocusOB {
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;
}
