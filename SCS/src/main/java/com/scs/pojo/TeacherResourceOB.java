package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResourceOB {
    private Integer authorityId;
    private Integer parentId;
    private String fileName;
    private String fileSize;
    private String level;
    private String createTime;
    private String teacherName;
    private Integer fileId;
    private boolean haveChild;
    private List<TeacherResourceOB> children;
    private String teacherId;
    private Integer focusId;
    private String courseName;
}
