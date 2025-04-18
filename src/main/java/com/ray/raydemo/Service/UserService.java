package com.ray.raydemo.Service;

import com.ray.raydemo.dto.LoginResponse;
import com.ray.raydemo.model.User;
import com.ray.raydemo.repository.UserRepository;
import com.ray.raydemo.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Username or email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // 驗證用戶並生成 JWT 令牌
    public LoginResponse authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用戶名或密碼錯誤"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用戶名或密碼錯誤");
        }
        String token = jwtUtil.generateToken(username);
        return new LoginResponse(token, "登錄成功");
    }
}
