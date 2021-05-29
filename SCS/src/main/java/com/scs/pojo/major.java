package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class major {
    private Integer majorId;//专业ID
    private String majorName;//专业名称
    private String majorType;//专业类
    private String majorYear;//修业年限
    private String academy;//所属学院
}

