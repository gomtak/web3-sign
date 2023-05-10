package com.example.demo;

import org.web3j.crypto.Hash;

public class HashUtils {

    public static byte[] sha3(byte[] input) {
        return Hash.sha3(input);
    }

    public static byte[] sha3(String input) {
        return Hash.sha3(input.getBytes());
    }
}
