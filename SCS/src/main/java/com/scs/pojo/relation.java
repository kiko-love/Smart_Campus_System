package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class relation {
    private String relationId;
    private String majorId;
    private String majorName;
    private String classId;
    private String classes;
    private String courseId;
    private String courseName;
    private String teacherId;
    private String teacherName;

}
