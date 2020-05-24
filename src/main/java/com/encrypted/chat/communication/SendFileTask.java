package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import com.encrypted.chat.encryption.AES;
import com.encrypted.chat.encryption.EncryptionMode;
import com.encrypted.chat.encryption.IvSpecProvider;
import javafx.concurrent.Task;

import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.Socket;

public class SendFileTask extends Task<Void> {
    private final String receiverIp;
    private final File fileToSend;
    private final byte[] key;
    private final EncryptionMode mode;

    public SendFileTask(String receiverIp, File fileToSend, byte[] key, EncryptionMode mode) {
        this.receiverIp = receiverIp;
        this.fileToSend = fileToSend;
        this.key = key;
        this.mode = mode;
    }

    @Override
    protected Void call() {
        try (
                Socket socket = new Socket(receiverIp, Main.isDev ? 4040 : 1234);
                ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
        ) {
            if (fileToSend != null) {
                System.out.println("Sent a file.");
                IvParameterSpec iv = IvSpecProvider.getInitialVector(null);
                byte[] encryptedFileName = AES.encrypt(fileToSend.getName().getBytes(), key, mode, iv);

                outObject.writeObject(new ExternalMessage(ExternalMessageType.FILE_MESSAGE,
                        encryptedFileName,
                        iv.getIV()));
                outObject.flush();
                outObject.reset();

                InputStream in = new FileInputStream(fileToSend);
                long totalSize = fileToSend.length();
                long doneSize = 0;
                int count;
                byte[] buffer = new byte[4096];
                while ((count = in.read(buffer)) > 0) {
                    doneSize += 4096;
                    updateProgress(doneSize, totalSize);
                    outObject.write(AES.encrypt(buffer, key, mode, iv), 0, count);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
