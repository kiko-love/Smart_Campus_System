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
    private Integer id;
    private String fileName;
    private String fileSize;
    private Date createTime;
    private Integer fileId;
    private boolean haveChild;
    private String courseName;
    private List<TeacherResourceOB> children;
}
