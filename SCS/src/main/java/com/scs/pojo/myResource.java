package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class myResource {
    private Integer focusId;
    private String userId;
    private String courseName;
    private String teacherId;
    private String focusTime;

}
