package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogOB {
    private String recordId;
    private String broswerName;
    private String operateSystem;
    private String ipAdress;
    private String logTime;
    private String logUser;
    private String userRole;
    private String logStatus;
}
