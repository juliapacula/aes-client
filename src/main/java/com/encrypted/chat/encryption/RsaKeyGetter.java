package com.encrypted.chat.encryption;

import com.encrypted.chat.Main;

import javax.crypto.CipherInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaKeyGetter {
    private static final String privateKeyPath = Main.isDev ? "private_2.key" : "private.key";
    private static final String publicKeyPath = Main.isDev ? "public_2.key" : "public.key";

    public static KeyPair getKeys(String password) {
        File privateKeyFile = new File(privateKeyPath);
        File publicKeyFile = new File(publicKeyPath);

        if (privateKeyFile.exists() && publicKeyFile.exists()) {
            PublicKey publicKey;
            PrivateKey privateKey;
            try {
                FileInputStream privateFileInputStream = new FileInputStream(privateKeyPath);
                DataInputStream dataInputStream = new DataInputStream(privateFileInputStream);
                byte[] iv = new byte[128 / 8];
                dataInputStream.read(iv);

                CipherInputStream privateCipherInputStream = new CipherInputStream(dataInputStream, AES.decryptCipher(
                        SHA256.hash(password),
                        EncryptionMode.CBC,
                        IvSpecProvider.getInitialVector(iv)));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int numberOfBytesRead;
                while ((numberOfBytesRead = privateCipherInputStream.read(buffer)) >= 0) {
                    byteArrayOutputStream.write(buffer, 0, numberOfBytesRead);
                }
                byte[] privateKeyDecryptedBytes = byteArrayOutputStream.toByteArray();

                PKCS8EncodedKeySpec encodedPrivateKeySpec = new PKCS8EncodedKeySpec(privateKeyDecryptedBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                privateKey = keyFactory.generatePrivate(encodedPrivateKeySpec);
            } catch (Exception e) {
                System.out.println(e.toString());
                privateKey = KeyGenerator.getRsaKeys().getPrivate();
            }

            try {
                byte[] publicKeyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
                X509EncodedKeySpec encodedPublicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                publicKey = keyFactory.generatePublic(encodedPublicKeySpec);
            } catch (Exception e) {
                System.out.println(e.toString());

                return KeyGenerator.getRsaKeys();
            }

            return new KeyPair(publicKey, privateKey);
        } else {
            return KeyGenerator.getRsaKeys();
        }
    }
}
