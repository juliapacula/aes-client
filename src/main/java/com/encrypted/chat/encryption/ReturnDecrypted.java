package com.encrypted.chat.encryption;

public interface ReturnDecrypted {
    byte[] function(byte[] input, byte[] iv);
}
