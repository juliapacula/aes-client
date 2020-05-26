package com.encrypted.chat.state;

import com.encrypted.chat.communication.ExternalFileMessage;
import com.encrypted.chat.communication.ExternalMessage;
import com.encrypted.chat.encryption.AES;
import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.encryption.IvSpecProvider;
import com.encrypted.chat.message.FileMessage;
import com.encrypted.chat.message.Message;
import com.encrypted.chat.message.MessageOwner;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class Reducer implements SessionReducer {
    private final Store store;

    public Reducer(Store store) {
        this.store = store;
    }

    public void addSelfMessage(String text) {
        Message message = new Message(text, MessageOwner.SELF);
        store.addMessage(message);
    }

    public void addSelfFileMessage(File file) {
        FileMessage message = new FileMessage(file.getName(), file.getAbsolutePath(), MessageOwner.SELF);
        store.addMessage(message);
    }

    public void setReceivedPublicKey(byte[] key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(key);

            store.receivedPublicKey = keyFactory.generatePublic(encodedKeySpec);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void setSelfPrivateKey(PrivateKey key) {
        store.selfPrivateKey = key;
    }

    public void setDecryptionSessionKey(byte[] decryptionSessionKey) {
        store.decryptionSessionKey = decryptionSessionKey;
    }

    public void setEncryptionSessionKey(byte[] encryptionSessionKey) {
        store.encryptionSessionKey = encryptionSessionKey;
    }

    public void addIncomingMessage(ExternalMessage externalMessage) {
        byte[] decryptedMessage = AES.decrypt(externalMessage.content,
                store.getDecryptionSessionKey(),
                store.getEncryptionMode(),
                IvSpecProvider.getInitialVector(externalMessage.iv));

        Message message = new Message(
                new String(decryptedMessage),
                MessageOwner.INCOMING);
        store.addMessage(message);
    }

    public void addIncomingFileMessage(ExternalFileMessage externalFileMessage) {
        byte[] decryptedFileName = externalFileMessage.fileName;

        byte[] decryptedFileContent = externalFileMessage.content;

        if (decryptedFileContent == null || decryptedFileName == null) {
            System.out.println("Could not decrypt file.");
            return;
        }

        String fileName = new String(decryptedFileName);
        File fileSaved = new File(System.getProperty("user.dir") + File.separator + fileName);

        try {
            fileSaved.createNewFile();
            FileOutputStream fos = new FileOutputStream(fileSaved);
            fos.write(decryptedFileContent);
            fos.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        FileMessage message = new FileMessage(fileName, fileSaved.getAbsolutePath(), MessageOwner.INCOMING);
        store.addMessage(message);
    }

    public void changeEncryptionMode(EncryptionMode mode) {
        store.setEncryptionMode(mode.toString());
    }

    public void connectToClient() {
        store.setConnected();
    }
}
