package com.encrypted.chat.encryption;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSA {
    public static byte[] encrypt(byte[] input, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] input, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
