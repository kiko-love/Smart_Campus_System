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
    private String courseTime;
}

