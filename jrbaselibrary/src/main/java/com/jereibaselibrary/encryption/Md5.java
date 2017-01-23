package com.jereibaselibrary.encryption;

import java.security.MessageDigest;

public class Md5 {
    /**
     * *
     *
     * @param plainText 需要加密的字符串
     * @param md5Format true 32位，false 16 位
     * @param charset   字符集格式
     * @return
     */
    public static String getMd5(String plainText, boolean md5Format, String charset) {
        String str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.trim().getBytes(charset));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (md5Format) {
                str = buf.toString().toUpperCase();
            } else {
                str = buf.toString().substring(8, 24).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;

    }
}
