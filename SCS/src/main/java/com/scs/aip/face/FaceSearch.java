package com.scs.aip.face;

import com.baidu.aip.face.AipFace;
import com.scs.aip.auth.NewAipFace;
import org.json.JSONObject;

import java.util.HashMap;

public class FaceSearch {
    public static AipFace client = NewAipFace.client;
    public static String faceSearch(String img) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "1");
        options.put("match_threshold", "70");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        String image = img;
        String imageType = "BASE64";
        String groupIdList = "face_group";

        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupIdList, options);
        System.out.println(res.toString(2));
        return res.toString();
    }
}
