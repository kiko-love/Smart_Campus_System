package com.scs.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonDataUtils {
    private String id;
    private String title;
    private boolean last;
    private String parentId;
    private checkArrUtils checkArr;
    private List<JsonDataUtils> children;
    private basicDataUtils basicData;
}


