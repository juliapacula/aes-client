package com.encrypted.chat.state;

import com.encrypted.chat.encryption.EncryptionMode;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface SessionStore {
    public byte[] getDecryptionSessionKey();
    public byte[] getEncryptionSessionKey();
    public PublicKey getReceivedPublicKey();
    public PrivateKey getSelfPrivateKey();
    public EncryptionMode getEncryptionMode();
}
