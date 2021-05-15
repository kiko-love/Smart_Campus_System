package com.scs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformToFront {

    private String msg;
    private String code;
    private String role;
    private String Status;
    private List<Object> data;


    @Override
    public String toString() {
        return "InformToFront{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", role='" + role + '\'' +
                ", Status='" + Status + '\'' +
                ", data=" + data +
                '}';
    }
}
