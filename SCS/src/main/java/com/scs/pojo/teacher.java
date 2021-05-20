package com.scs.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class teacher {
    private String teacherId;
    private String realName;
    private String phone;
    private String sex;
    private String collegeId;
    private String addr;
    private Date birthday;

}
