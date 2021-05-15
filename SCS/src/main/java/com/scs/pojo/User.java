package com.scs.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String userName;
    private String md5password;
    private String role;
    private String status;
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", md5password='" + md5password + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
