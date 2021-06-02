package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherOfCourseOB {
    private Integer focusId;
    private String teacherId;
    private String teacherName;
}
