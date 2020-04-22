package com.encrypted.chat.encryption;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RsaGenerator {
    private String password;

    public RsaGenerator(String userPassword) {

    }

    public void generateKeyPairs() {
        String encodedPassword = getEncodedPassword();
    }

    private String getEncodedPassword() {
        String encodedPassword = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            encodedPassword = bytesToHex(encodedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encodedPassword;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
