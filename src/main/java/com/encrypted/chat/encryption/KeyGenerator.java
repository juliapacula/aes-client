package com.encrypted.chat.encryption;

import java.security.*;

public class KeyGenerator {
    public static KeyPair getRsaKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyPairGenerator.initialize(1024, random);

            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getSessionKey() {
        SecureRandom r = new SecureRandom();
        byte[] aesKey = new byte[16];
        r.nextBytes(aesKey);

        return aesKey;
    }
}
