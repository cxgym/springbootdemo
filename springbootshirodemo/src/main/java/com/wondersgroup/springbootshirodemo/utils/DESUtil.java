package com.wondersgroup.springbootshirodemo.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DESUtil {

    public final static String KEY = "chronic_2019";
    private final static String DES = "DES";

    /**
     * Description 根据键值进行加密
     *
     * @param data 加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes("UTF-8"), key.getBytes());
        String strs = Base64.encodeBase64URLSafeString(bt);
        return strs;
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data 加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null || key == null)
            return null;
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt, "UTF-8");
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("cIzMSq3eLex0h6z7LEMzyp3wFkpkfcp1DdUi25HjGRXSy1Rkgjvu4SgKEq6jSLUby6Y_X0agDTLEveui8ypW93rwIqJF1JH0sPQ3gn7HzDM",KEY));
    }
}
