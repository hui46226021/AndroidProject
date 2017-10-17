package com.jrbaselibrary.encryption;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by zhush on 2017/1/23.
 * E-mail 405086805@qq.com
 * PS  加密工具
 *
 * desEncrypt  Des加密
 * desDecode   Des解密
 * md5Encrypt  MD5加密
 * rsaEncrypt  RSA加密
 * resdecrypt  RSA解密
 */
public class EncryptUtils {
    /**
     * Des 加密
     * @param salt  盐值 （8位）
     * @param str   要加密的字符串
     * @return
     */
    public static String desEncrypt(String salt,String str){
        if(salt.length()>8){
            throw new RuntimeException("盐值不能大于8位");
        }
        return Des.encode(salt, str);
    }

    /**
     * Des 解密
     * @param salt
     * @param str
     * @return
     */
    public static String desDecode(String salt,String str){
        if(salt.length()>8){
            throw new RuntimeException("盐值不能大于8位");
        }
        return Des.decodeValue(salt, str);
    }

    /**
     * *
     * MD5 加密
     * @param plainText 需要加密的字符串
     * @param md5Format true 32位，false 16 位
     * @param charset   字符集格式
     * @return
     */
    public static String md5Encrypt(String plainText, boolean md5Format, String charset) {
       return Md5.getMd5(plainText, md5Format, charset);
    }
    /**
     * *
     * RSA加密
     * @param str 需要加密的字符串
     * @param modulus 模数
     * @param exponent 指数
     * @return
     */
    public static String rsaEncrypt(String str,String modulus,String exponent){
        RSAPublicKey publicKey = RSAUtils.getPublicKey(modulus, exponent);
        //加密*/
       String miStr  =null;
        try {
            miStr   =  RSAUtils.encryptByPublicKey(str, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miStr;
    }



    /**
     * *
     * RSA解密
     * @param str 需要加密的字符串
     * @param modulus 模数
     * @param exponent 指数
     * @return
     */
    public static String resdecrypt(String str,String modulus,String exponent){
        RSAPrivateKey privateKey = RSAUtils.getPrivateKey(modulus, exponent);
        //加密*/
        String miStr  =null;
        try {
            miStr   =  RSAUtils.decryptByPrivateKey(str, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miStr;
    }


}
