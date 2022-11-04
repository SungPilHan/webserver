package com.eqstwah.webserver.Utility;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.Setter;

@Setter
public class AES256 {
    public static String alg = "AES/CBC/PKCS5Padding";
    private String key; // 32bytes
    private String iv; // 16bytes

    public AES256(){
        this.key = "testtesttesttesttesttesttesttest";
        this.iv = "application_hack";
    }


    public AES256(String key, String iv){
        this.key = key;
        this.iv = iv;
    }

    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
    }

}
