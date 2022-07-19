package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {

    // 生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    // hello -> abc123def456
    // hello + 3e4a8 -> abc123def456abc
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 函数的功能：将服务器传来的code、msg和map
     *           等信息封装成一个json格式的字符串
     * @param code：服务器返回给浏览器的编号
     * @param msg：返回的提示
     * @param map：返回的业务数据
     * @return 返回一个json格式的字符串，比如：{"msg":"ok","code":0,"name":"zhangsan","age":25}
     */
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    // 根据服务器返回的信息来重载前面的方法
    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    // 根据服务器返回的信息来重载前面的方法
    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

    // 测试一下
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 25);
        System.out.println(getJSONString(0, "ok", map));
    }

}
