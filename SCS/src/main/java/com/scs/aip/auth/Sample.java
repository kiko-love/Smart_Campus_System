package com.scs.aip.auth;


import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.util.HashMap;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "23940837";
    public static final String API_KEY = "0zjph1CSKBMM7GUTFnOy1GUz";
    public static final String SECRET_KEY = "OfWyg7kowek0U3z9PphOOj7c5dDHwZNs";

    public static void main(String[] args) {
        // 初始化一个AipFace
        AipFace client = NewAipFace.client;

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
        String imageType = "BASE64";

        // 人脸检测
        HashMap<String, String> options = null;
        JSONObject res = client.detect(image, imageType, options);
        System.out.println(res.toString(2));

    }
}
