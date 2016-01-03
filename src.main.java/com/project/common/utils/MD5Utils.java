package com.project.common.utils;

import java.security.MessageDigest;

/**
 * Created by liucf6 on 2015/8/27.
 */
public class MD5Utils {

    public static String md5Digest(String src)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(src.getBytes("utf-8"));
            return byte2HexStr(b);
        }catch (Exception e){
            
        }
        return null;
    }

    private static String byte2HexStr(byte[] b)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1){
                sb.append("0");
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
