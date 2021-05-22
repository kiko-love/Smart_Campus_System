package com.scs.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class course {
    private String courseId;
    private String courseName;
    private String courseScore;
    //学时
    private String courseTime;
    //开设学院
    private String courseMajor;
    @Override
    public String toString() {
        return "course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseScore='" + courseScore + '\'' +
                ", courseTime='" + courseTime + '\'' +
                ", courseMajor='" + courseMajor + '\'' +
                '}';
    }
}

