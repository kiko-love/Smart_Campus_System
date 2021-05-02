package com.scs.aip.face;

import com.baidu.aip.face.AipFace;
import com.scs.aip.auth.NewAipFace;
import org.json.JSONObject;

import java.util.HashMap;

public class FaceDetect {
    public static AipFace client = NewAipFace.client;

    public static String faceDetect(String img) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "faceshape,facetype,gender,mask,glasses,age");
        options.put("max_face_num", "2");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "LOW");
        String image = img;
        String imageType = "BASE64";
        // 人脸检测
        JSONObject res = client.detect(image, imageType, options);
        System.out.println(res.toString(2));
        return res.toString();
    }
}
