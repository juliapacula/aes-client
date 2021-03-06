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
import java.net.*;

public class ReceiveMessageService extends ScheduledService<ExternalMessage> {
    private final ReturnDecrypt returnDecrypt;
    private final ReturnDecrypted returnDecrypted;

    ReceiveMessageService(ReturnDecrypt returnDecrypt, ReturnDecrypted returnDecrypted) {
        setDelay(Duration.ONE);
        this.returnDecrypt = returnDecrypt;
        this.returnDecrypted = returnDecrypted;
        setRestartOnFailure(true);
    }

    @Override
    protected Task<ExternalMessage> createTask() {
        return new Task<ExternalMessage>() {
            @Override
            protected ExternalMessage call() {
                ExternalMessage message = null;
                System.out.println("Started listening.");

                try (
                        ServerSocket serverSocket = new ServerSocket(8005, 0, InetAddress.getByName(Main.localIpAddress));
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
                            try {
                                while ((count = cipherInputStream.read(buffer)) > 0) {
                                    byteArrayOutputStream.write(buffer, 0, count);
                                }
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                            cipherInputStream.close();
                            byteArrayOutputStream.flush();
                            byteArrayOutputStream.close();

                            return new ExternalFileMessage(returnDecrypted.function(message.content, iv), byteArrayOutputStream.toByteArray(), message.iv);
                        } else {
                            System.out.println("Received a message.");
                        }
                    }
                } catch (Exception ex) {
                    failed();
                    System.out.println("Restarting receiver. Exception occurred:" + ex.toString());
                }

                return message;
            }
        };
    }
}
