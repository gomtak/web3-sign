package com.example.demo;

import lombok.Data;

@Data
public class RegisterRequest {
    private String walletAddress;
    private String signature;
}
