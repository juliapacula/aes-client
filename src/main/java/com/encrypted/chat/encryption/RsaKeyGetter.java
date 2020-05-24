package com.encrypted.chat.encryption;

import com.encrypted.chat.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaKeyGetter {
    private static final String privateKeyPath = Main.isDev ? "private_2.key" : "private.key";
    private static final String publicKeyPath = Main.isDev ? "private_2.key" : "public.key";

    public static KeyPair getKeys(String password) {
        File privateKeyFile = new File(privateKeyPath);
        File publicKeyFile = new File(publicKeyPath);

        if (privateKeyFile.exists() && publicKeyFile.exists()) {
            try {
                byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
                // TODO: Decrypt
                PKCS8EncodedKeySpec encodedPrivateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(encodedPrivateKeySpec);

                byte[] publicKeyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
                X509EncodedKeySpec encodedPublicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

                PublicKey publicKey = keyFactory.generatePublic(encodedPublicKeySpec);

                return new KeyPair(publicKey, privateKey);
            } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return KeyGenerator.getRsaKeys();
        }
    }
}
