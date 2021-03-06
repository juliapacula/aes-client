package com.encrypted.chat.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    public static byte[] encrypt(byte[] input, byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            Cipher cipher = encryptCipher(hashedKey, encryptionMode, iv);

            return Base64.getEncoder().encode(cipher.doFinal(input));
        } catch (Exception e) {
            return Base64.getEncoder().encode(input);
        }
    }

    public static byte[] decrypt(byte[] input, byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            Cipher cipher = decryptCipher(hashedKey, encryptionMode, iv);

            return cipher.doFinal(Base64.getDecoder().decode(input));
        } catch (Exception e) {
            return Base64.getDecoder().decode(input);
        }
    }

    public static Cipher decryptCipher(byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(hashedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + encryptionMode.toString() + "/PKCS5Padding");
            if (encryptionMode.equals(EncryptionMode.ECB)) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            }

            return cipher;
        } catch (Exception e) {
            return null;
        }
    }

    public static Cipher encryptCipher(byte[] hashedKey, EncryptionMode encryptionMode, IvParameterSpec iv) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(hashedKey, "AES");
            Cipher cipher = Cipher.getInstance("AES/" + encryptionMode.toString() + "/PKCS5Padding");
            if (encryptionMode.equals(EncryptionMode.ECB)) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            }
            return cipher;
        } catch (Exception e) {
            return null;
        }
    }
}
