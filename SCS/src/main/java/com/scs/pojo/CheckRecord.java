package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckRecord {
    private String teacherId;
    private Date checkDate;
    private String realName;

    @Override
    public String toString() {
        return "CheckRecord{" +
                "teacherId='" + teacherId + '\'' +
                ", checkDate=" + checkDate +
                ", realName='" + realName + '\'' +
                '}';
    }
}
