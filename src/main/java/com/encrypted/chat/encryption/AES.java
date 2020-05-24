package com.encrypted.chat.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    public static byte[] encrypt(byte[] input, byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(hashedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + encryptionMode.toString() + "/PKCS5Padding");
            if (encryptionMode.equals(EncryptionMode.ECB)) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            }
            return Base64.getEncoder().encode(cipher.doFinal(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] input, byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(hashedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + encryptionMode.toString() + "/PKCS5Padding");
            if (encryptionMode.equals(EncryptionMode.ECB)) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            }

            return cipher.doFinal(Base64.getDecoder().decode(input));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
