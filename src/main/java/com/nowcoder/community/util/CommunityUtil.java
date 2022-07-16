package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    // 生成随机的字符串

    /**
     * Java中使用UUID来生成随机字符串，因为直接生成的随机字符串
     * 中含有"-"，我们在项目中使用的字符串不想含有"-",因此加了replaceAll()
     * 方法将随机字符串中的"-"替换为空字符。
     *
     * @return 返回一个随机字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密
    // hello -> abc123def456; hello + 3e4a8 -> abc123def456abc
    // 为了提高密码的安全性，我们给密码加上一个随机字符串(sault)，然后再进行加密操纵。
    public static String md5(String key) {
        // 当key为空、为空格、为null时，则认为是空的，无需处理，返回null即可。
        if(StringUtils.isBlank(key)){
            return null;
        }
        // 加密
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}