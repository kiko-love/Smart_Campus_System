package com.scs.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RandomCodeUtils {
    //连接本地的 Redis 服务
    public static Jedis jedis;

    // 随机验证码
    public static String achieveCode() {  //由于数字 1 、 0 和字母 O 、l 有时分不清楚，所以，没有数字 1 、 0
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z"};
        List list = Arrays.asList(beforeShuffle);//将数组转换为集合
        Collections.shuffle(list);  //打乱集合顺序
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)); //将集合转化为字符串
        }
        String code = sb.toString().substring(3, 7);
        jedis = JedisUtil.getJedis();
        boolean keyExist = jedis.exists("EmailCode");
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        if (keyExist) {
            jedis.del("EmailCode");
        }
        jedis.set("EmailCode", code);
        jedis.pexpire("EmailCode", 60000);
        Long EmailCode_time = jedis.pttl("EmailCode");
        System.out.println(EmailCode_time);
        //System.out.printf(jedis.get("EmailCode"));
        return code; //截取字符串第4到8
    }

    //设置邮箱验证成功后的密码修改凭证(token)
    public static boolean setEmailToken(String username, String token, long tokenTime) {
        String key = "EMAIL_CODE_VER_" + username;
        jedis = JedisUtil.getJedis();
        Long EmailCode_tokenTime = jedis.pttl(key);
        System.out.println("setEmailToken" + EmailCode_tokenTime);
        jedis.set(key, token);
        jedis.pexpire(key, tokenTime);
        return true;
    }

    //验证邮箱验证成功后的密码修改凭证(token)
    public static boolean verEmailToken(String key, String token) {
        Long EmailCode_tokenTime = jedis.pttl(key);
        System.out.println("verEmailToken" + EmailCode_tokenTime);
        if (EmailCode_tokenTime != -2) {
            if (token.equals(jedis.get(key))) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int verEmailCode(String code) {
//        boolean keyExist = jedis.exists("EmailCode");
        // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
        //System.out.println("emailCode=" + jedis.get("EmailCode"));
        Long EmailCode_time = jedis.pttl("EmailCode");
        System.out.println("verEmailCode" + EmailCode_time);

        //-2代表redis中该key不存在或已经过期
        if (EmailCode_time != -2) {
            String emailCode = jedis.get("EmailCode");
            System.out.println("emailCode=" + emailCode);
            if (code.equals(emailCode) && jedis.pttl("EmailCode") > 0) {
                JedisUtil.close(jedis);
                return 100;
            } else {
                JedisUtil.close(jedis);
                return 110;
            }
        } else {
            JedisUtil.close(jedis);
            return -1;
        }
    }


    public static void main(String[] args) throws EmailException {
        List<String> list = new ArrayList<>();
        list.add("2350262486@qq.com");
        sendEmail(list);
    }

    public static String sendEmail(List<String> desEmails) throws EmailException {
        HtmlEmail email = new HtmlEmail();//创建一个HtmlEmail实例对象
        email.setHostName("smtp.qq.com");
        email.setCharset("utf-8");//设置发送的字符类型
        for (String desEmail : desEmails) {
            email.addTo(desEmail);//设置收件人
        }
        //发送人的邮箱为自己的，用户名可以随便填
        email.setFrom("3300303281@qq.com", "智慧校园系统");
        //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setAuthentication("3300303281@qq.com", "uxbnheikfxhzdbfb");
        email.setSubject("智慧校园系统-找回密码验证");//设置发送主题
        email.setMsg("尊敬的用户:你好!\n" +
                "你正在使用智慧校园系统-邮箱验证重置密码\n" +
                "您本次操作的验证码为:<b>" + achieveCode() + "</b>\n" +
                "(有效期为一分钟)");//设置发送内容
        return email.send();//进行发送

    }

}


