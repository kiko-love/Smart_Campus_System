package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class portrait {
    private String userId;
    private String role;
    private String p_name;
    private String p_path;

}
