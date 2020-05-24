package com.encrypted.chat.encryption;

import com.encrypted.chat.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;

public class RsaKeySaver {
    private static final String privateKeyPath = Main.isDev ? "private_2.key" : "private.key";
    private static final String publicKeyPath = Main.isDev ? "private_2.key" : "public.key";

    public static void saveKeys(KeyPair keyPair, String password) {
        try {
            // TODO: Encrypt
            FileOutputStream privateOutputStream = new FileOutputStream(privateKeyPath);
            privateOutputStream.write(keyPair.getPrivate().getEncoded());
            privateOutputStream.close();
            FileOutputStream publicOutputStream = new FileOutputStream(publicKeyPath);
            publicOutputStream.write(keyPair.getPublic().getEncoded());
            publicOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
