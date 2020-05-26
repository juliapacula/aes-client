package com.encrypted.chat.encryption;

import com.encrypted.chat.Main;

import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.KeyPair;

public class RsaKeySaver {
    private static final String privateKeyPath = Main.isDev ? "private_2.key" : "private.key";
    private static final String publicKeyPath = Main.isDev ? "public_2.key" : "public.key";

    public static void saveKeys(KeyPair keyPair, String password) {
        File privateKeyFile = new File(privateKeyPath);
        File publicKeyFile = new File(publicKeyPath);

        if (privateKeyFile.exists() && publicKeyFile.exists()) {
            return;
        }

        try {
            IvParameterSpec ivParameterSpec = IvSpecProvider.getInitialVector(null);

            FileOutputStream privateOutputStream = new FileOutputStream(privateKeyPath);
            DataOutputStream dataOutputStream = new DataOutputStream(privateOutputStream);
            dataOutputStream.write(ivParameterSpec.getIV());
            CipherOutputStream privateCipherOutputStream = new CipherOutputStream(dataOutputStream, AES.encryptCipher(
                    SHA256.hash(password),
                    EncryptionMode.CBC,
                    ivParameterSpec
            ));
            privateCipherOutputStream.write(keyPair.getPrivate().getEncoded());
            privateCipherOutputStream.flush();
            privateCipherOutputStream.close();

            FileOutputStream publicOutputStream = new FileOutputStream(publicKeyPath);
            publicOutputStream.write(keyPair.getPublic().getEncoded());
            publicOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
