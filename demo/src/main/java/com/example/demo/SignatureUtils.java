package com.example.demo;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SignatureException;

public class SignatureUtils {

    public static String ecRecover(String message, String signature) throws SignatureException {
        byte[] messageHash = HashUtils.sha3(message.getBytes());
        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        if (signatureBytes.length != 65) {
            throw new SignatureException("Invalid signature length");
        }
        byte[] r = new byte[32];
        byte[] s = new byte[32];
        System.arraycopy(signatureBytes, 0, r, 0, 32);
        System.arraycopy(signatureBytes, 32, s, 0, 32);
        Sign.SignatureData signatureData = new Sign.SignatureData(signatureBytes[64], r, s);
        BigInteger publicKey = Sign.signedMessageToKey(messageHash, signatureData);
        ECKeyPair ecKeyPair = ECKeyPair.create(publicKey);
        Credentials credentials = Credentials.create(ecKeyPair);
        return credentials.getAddress();
    }
}
