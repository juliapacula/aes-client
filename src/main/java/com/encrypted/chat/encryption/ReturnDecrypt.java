package com.encrypted.chat.encryption;

import javax.crypto.Cipher;

public interface ReturnDecrypt {
    Cipher cipher(byte[] iv);
}
