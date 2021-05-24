package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class resource {
    private Integer fileId;
    private String fileName;
    private String teacherId;
    private String filePath;
    private String courseName;
    private Date createTime;
    private String filesize;

}
