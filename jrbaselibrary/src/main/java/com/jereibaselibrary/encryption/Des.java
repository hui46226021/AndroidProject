package com.jereibaselibrary.encryption;






import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class Des {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static String privateKey = "%";

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * 异常
     */
    public static String encode(String key, String data) {

        try {
            return encode(privateKey + key, reverse(data).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * 异常
     */
    public static String encode(String key, byte[] data) {

        try {
            DESKeySpec dks = new DESKeySpec((privateKey + key).getBytes());

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("********".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static byte[] decode(String key, byte[] data) {
        try {
            DESKeySpec dks = new DESKeySpec((privateKey + key).getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("********".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取编码后的值
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String decodeValue(String key, String data) {

        byte[] datas;
        String value = null;

        datas = decode(privateKey + key, Base64.decode(data,Base64.DEFAULT));

        value = new String(datas);
        if (value.equals("")) {
            return null;
        }
        return reverse(value);
    }

    public static String reverse(String string) {
        StringBuffer temp = new StringBuffer(string);
        return (temp.reverse()).toString();
    }

 /*   public static void main(String[] args) {
        String key = "2312222";
        String data = "bbbbbbbbbbb";
        String temp = encode(key, data);
        System.out.println("加密：" + temp);
        System.out.println("解密：" + decodeValue("222", temp));
    }*/

}