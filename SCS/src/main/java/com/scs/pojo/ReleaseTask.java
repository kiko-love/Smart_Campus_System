package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseTask {
    private String teacherId;
    private String courseId;
    private String classes;
    private String taskName;
    private String ReleaseTaskPath;
    private String TaskClaim;
}
