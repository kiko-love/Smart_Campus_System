package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FocusResInfoOB {
    private String courseId;
    private String courseName;
    private String teacherName;
    private String teacherId;
}
