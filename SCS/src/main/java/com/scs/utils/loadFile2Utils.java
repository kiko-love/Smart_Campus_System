package com.scs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class loadFile2Utils {
    private String id;
    private String tittle;
    private boolean last;
    private String parentId;
    private List<loadFile2Utils> children;
    private basicDataUtils basicData;
}


