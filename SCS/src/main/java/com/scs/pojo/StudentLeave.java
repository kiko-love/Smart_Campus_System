package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLeave {
    private Integer leaveId;
    private String userId;
    private String startTime;
    private String endTime;
    private String leaveReason;
    private String rejectInfo;
    private Integer status;  // 0已拒绝，1等待审批，2已通过
    private String counselorId;
}
