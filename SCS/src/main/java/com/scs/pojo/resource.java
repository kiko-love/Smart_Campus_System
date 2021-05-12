package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class resource {
    private String fileId;
    private String fileName;
    private String teacherId;
    private String filePath;
    private String profession;

}
