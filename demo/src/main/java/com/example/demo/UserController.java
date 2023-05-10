package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SignatureException;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // 요청 바디에서 지갑 주소와 서명 값을 가져온다.
        String walletAddress = registerRequest.getWalletAddress();
        String signature = registerRequest.getSignature();

        // 검증할 메시지를 생성한다.
        String message = "Register " + walletAddress;

        // 서명 값에서 복원된 지갑 주소를 가져온다.
        String recoveredAddress = null;
        try {
            recoveredAddress = SignatureUtils.ecRecover(message, signature);
        } catch (SignatureException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        // 복원된 지갑 주소와 사용자가 제공한 지갑 주소가 일치하지 않으면 에러 메시지를 반환한다.
        if (!recoveredAddress.equalsIgnoreCase(walletAddress)) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        // 유저 정보를 저장한다.
        User user = new User(walletAddress);
        userRepository.save(user);

        return ResponseEntity.ok("User saved to database");
    }
}
