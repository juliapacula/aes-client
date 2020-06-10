package com.encrypted.chat.communication;

import com.encrypted.chat.encryption.*;
import com.encrypted.chat.state.Reducer;
import com.encrypted.chat.state.SessionStore;
import javafx.beans.property.ReadOnlyDoubleProperty;

import java.io.File;
import java.security.KeyPair;

public class ConnectionManager {
    private final Reducer reducer;
    private final SessionStore store;
    public String ipToSend;
    private ReceiveMessageService receiveMessageService;

    public ConnectionManager(Reducer reducer, SessionStore store) {
        this.reducer = reducer;
        this.store = store;
    }

    public void connectToClient(String ip, String userPassword) {
        ipToSend = ip;
        reducer.connectToClient();

        KeyPair rsaKeys = RsaKeyGetter.getKeys(userPassword);
        RsaKeySaver.saveKeys(rsaKeys, userPassword);
        sendMessage(new ExternalMessage(ExternalMessageType.PUBLIC_KEY, rsaKeys.getPublic().getEncoded()));
        reducer.setSelfPrivateKey(rsaKeys.getPrivate());
        sendSessionKey();
    }


    public void listenForMessages() {
        if (receiveMessageService == null) {
            receiveMessageService = new ReceiveMessageService(
                    ((iv) -> AES.decryptCipher(store.getDecryptionSessionKey(),
                            store.getEncryptionMode(),
                            IvSpecProvider.getInitialVector(iv))
                    ),
                    (input, iv) -> AES.decrypt(input, store.getDecryptionSessionKey(),
                            store.getEncryptionMode(),
                            IvSpecProvider.getInitialVector(iv))
            );
        }

        receiveMessageService.setOnSucceeded(event -> {
            ExternalMessage message = (ExternalMessage) event.getSource().getValue();
            reactToMessage(message);
        });

        receiveMessageService.start();
    }

    public ReadOnlyDoubleProperty sendMessage(ExternalMessage message) {
        SendMessageTask sendMessageTask = new SendMessageTask(ipToSend, message);
        Thread thread = new Thread(sendMessageTask);
        thread.setDaemon(true);
        thread.start();

        return sendMessageTask.progressProperty();
    }

    public ReadOnlyDoubleProperty sendFile(File fileToSend) {
        SendFileTask task = new SendFileTask(
                ipToSend,
                fileToSend,
                store.getEncryptionSessionKey(),
                store.getEncryptionMode()
        );
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        return task.progressProperty();
    }

    private void reactToMessage(ExternalMessage message) {
        switch (message.type) {
            case TEXT_MESSAGE:
                reducer.addIncomingMessage(message);
                break;
            case ENCRYPTION_TYPE:
                EncryptionMode mode = EncryptionMode.valueOf(new String(message.content));
                reducer.changeEncryptionMode(mode);
                break;
            case PUBLIC_KEY:
                System.out.println("Received RSA Public key.");
                reducer.setReceivedPublicKey(message.content);
                sendSessionKey();
                break;
            case SESSION_KEY:
                System.out.println("Received Session key.");
                try {
                    byte[] decryptedSessionKey = RSA.decrypt(message.content, store.getSelfPrivateKey());
                    reducer.setDecryptionSessionKey(decryptedSessionKey);
                } catch (Exception e) {
                    reducer.setDecryptionSessionKey(KeyGenerator.getSessionKey());
                }
                break;
            case FILE_MESSAGE:
                reducer.addIncomingFileMessage((ExternalFileMessage) message);
                break;
            default:
                break;
        }
    }

    private void sendSessionKey() {
        if (store.getSelfPrivateKey() != null && store.getReceivedPublicKey() != null) {
            byte[] sessionKey = KeyGenerator.getSessionKey();
            reducer.setEncryptionSessionKey(sessionKey);
            byte[] encryptedSessionKey = RSA.encrypt(sessionKey, store.getReceivedPublicKey());
            sendMessage(new ExternalMessage(ExternalMessageType.SESSION_KEY, encryptedSessionKey));
        }
    }
}
