package com.scs.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * IP地址工具类
 * @author Luz.Ho211
 * @date 2020-03-13 10:55
 */
public final class IPUtils {

    /**
     * 请求通过反向代理之后，可能包含请求客户端真实IP的HTTP HEADER
     * 如果后续扩展，有其他可能包含IP的HTTP HEADER，加到这里即可
     */
    private final static String[] POSSIBLE_HEADERS = new String[] {
            "X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP",
            "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
    };

    private IPUtils() {}

    /**
     * 获取请求客户端的真实IP地址
     * @param request javax.servlet.http.HttpServletRequest
     * @return 客户端端真实IP地址
     */
    public static String getRequestClientRealIP(HttpServletRequest request) {
        String ip;
        // 先检查代理：逐个HTTP HEADER检查过去，看看是否存在客户端真实IP
        for (String header : POSSIBLE_HEADERS) {
            ip = request.getHeader(header);
            if (isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 请求经过多次反向代理后可能会有多个IP值（以英文逗号分隔），第一个IP才是客户端真实IP
                return ip.contains(",") ? ip.split(",")[0] : ip;
            }
        }
        // 从所有可能的HTTP HEADER中都没有找到客户端真实IP，采用request.getRemoteAddr()来兜底
        ip = request.getRemoteAddr();
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            // 说明是从本机发出的请求，直接获取并返回本机IP地址
            return getLocalRealIP();
        }
        return ip;
    }

    /**
     * 获取本机IP地址
     * @return 若配置了外网IP则优先返回外网IP；否则返回本地IP地址。如果本机没有被分配局域网IP地址（例如本机没有连接任何网络），则默认返回127.0.0.1
     */
    public static String getLocalRealIP() {
        String localIP = "127.0.0.1"; // 本地IP
        String netIP = null; // 外网IP

        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            // 发生异常则返回null
            return null;
        }
        InetAddress ip;
        boolean netIPFound = false; // 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !netIPFound) {
            Enumeration<InetAddress> address = netInterfaces.nextElement().getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    // 外网IP
                    netIP = ip.getHostAddress();
                    netIPFound = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {
                    // 内网IP
                    localIP = ip.getHostAddress();
                }
            }
        }

        if (isNotBlank(netIP)) {
            // 如果配置了外网IP则优先返回外网IP地址
            return netIP;
        }
        return localIP;
    }

    /**
     * <pre>
     * isBlank(null)      = true
     * isBlank("")        = true
     * isBlank(" ")       = true
     * isBlank("bob")     = false
     * isBlank("  bob  ") = false
     * </pre>
     * @param cs 输入参数
     * @return
     */
    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <pre>
     * isNotBlank(null)      = false
     * isNotBlank("")        = false
     * isNotBlank(" ")       = false
     * isNotBlank("bob")     = true
     * isNotBlank("  bob  ") = true
     * </pre>
     * @param cs 输入参数
     * @return
     */
    private static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

}