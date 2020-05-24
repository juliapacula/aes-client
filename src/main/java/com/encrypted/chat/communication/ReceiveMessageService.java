package com.encrypted.chat.communication;

import com.encrypted.chat.Main;
import com.encrypted.chat.encryption.ReturnDecrypt;
import com.encrypted.chat.encryption.ReturnDecrypted;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import javax.crypto.CipherInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveMessageService extends ScheduledService<ExternalMessage> {
    private ReturnDecrypt returnDecrypt;
    private ReturnDecrypted returnDecrypted;

    ReceiveMessageService(ReturnDecrypt returnDecrypt, ReturnDecrypted returnDecrypted) {
        setDelay(Duration.ONE);
        this.returnDecrypt = returnDecrypt;
        this.returnDecrypted = returnDecrypted;
    }

    @Override
    protected Task<ExternalMessage> createTask() {
        return new Task<ExternalMessage>() {
            @Override
            protected ExternalMessage call() {
                ExternalMessage message = null;

                try (
                        ServerSocket serverSocket = new ServerSocket(Main.isDev ? 1234 : 4040, 0, InetAddress.getByName("127.0.0.1"));
                        Socket connectedSocket = serverSocket.accept();
                        ObjectInputStream in = new ObjectInputStream(connectedSocket.getInputStream());
                ) {
                    message = (ExternalMessage) in.readObject();
                    if (message != null) {

                        if (message.type == ExternalMessageType.FILE_MESSAGE) {
                            byte[] iv = message.iv;
                            CipherInputStream cipherInputStream = new CipherInputStream(in, returnDecrypt.cipher(iv));
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] buffer = new byte[512];
                            int count;
                            while ((count = cipherInputStream.read(buffer)) > 0) {
                                byteArrayOutputStream.write(buffer, 0, count);
                            }
                            cipherInputStream.close();
                            byteArrayOutputStream.flush();
                            byteArrayOutputStream.close();

                            return new ExternalFileMessage(returnDecrypted.function(message.content, iv), byteArrayOutputStream.toByteArray(), message.iv);
                        } else {
                            System.out.println("Received a message: " + new String(message.content));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return message;
            }
        };
    }
}
