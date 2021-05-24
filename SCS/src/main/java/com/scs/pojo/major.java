package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class major {
    private Integer majorId;
    private String majorName;
    private String majorType;
    private String academy;//所属学院
}

