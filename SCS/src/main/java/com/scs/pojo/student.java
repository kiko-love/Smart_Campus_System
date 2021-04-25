package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class student {
    private String userId;
    private String department;
    private String realName;
    private String sex;
    private String phone;
    private String levels;
}