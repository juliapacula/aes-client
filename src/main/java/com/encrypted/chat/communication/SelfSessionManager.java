package com.encrypted.chat.communication;

import com.encrypted.chat.encryption.KeyGenerator;
import com.encrypted.chat.encryption.RSA;
import com.encrypted.chat.encryption.RsaKeyGetter;
import com.encrypted.chat.state.SessionReducer;
import com.encrypted.chat.state.SessionStore;

import java.security.KeyPair;
import java.security.PublicKey;

public class SelfSessionManager {
    SessionStore store;
    SessionReducer reducer;
    ConnectionManager connectionManager;

    public SelfSessionManager(SessionStore store, SessionReducer reducer, ConnectionManager connectionManager) {
        this.store = store;
        this.reducer = reducer;
        this.connectionManager = connectionManager;
    }

    public void sendRsaKey(String userPassword) {
        KeyPair rsaKeys = RsaKeyGetter.getKeys(userPassword);
        connectionManager.sendMessage(new ExternalMessage(ExternalMessageType.PUBLIC_KEY, rsaKeys.getPublic().getEncoded()));
        reducer.setSelfPrivateKey(rsaKeys.getPrivate());
    }
}
