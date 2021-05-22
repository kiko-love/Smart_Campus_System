package com.scs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class basicDataUtils {
    private String teacherId;
    private String course;
    private Date createTime;
    private String filesize;
}
