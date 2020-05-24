package com.encrypted.chat.state;

import java.security.PrivateKey;

public interface SessionReducer {
    public void setReceivedPublicKey(byte[] key);
    public void setSelfPrivateKey(PrivateKey key);
    public void setDecryptionSessionKey(byte[] decryptionSessionKey);
    public void setEncryptionSessionKey(byte[] encryptionSessionKey);
}
