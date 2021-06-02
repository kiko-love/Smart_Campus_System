package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class classInfo {
    private String classes;//班级名
    private String classId;
    private String Major;//所属专业
    private String counselor;//辅导员
    private String counselorId;//辅导员Id
}
