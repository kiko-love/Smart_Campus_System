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
    private String Major;
    private String counselor;
}