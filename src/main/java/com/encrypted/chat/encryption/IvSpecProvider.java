package com.encrypted.chat.encryption;

import javax.crypto.spec.IvParameterSpec;
import java.util.Random;

public class IvSpecProvider {
    public static IvParameterSpec getInitialVector(byte[] givenIv){
        if (givenIv != null) {
            return new IvParameterSpec(givenIv);
        }

        byte[] iv = new byte[128/8];
        new Random().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
