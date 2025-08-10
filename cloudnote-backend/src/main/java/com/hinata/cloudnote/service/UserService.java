package com.hinata.cloudnote.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hinata.cloudnote.dto.UserRegistrationDto;
import com.hinata.cloudnote.entity.User;
import com.hinata.cloudnote.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User register(UserRegistrationDto dto) {
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("ユーザー名が既に存在します");
        }
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("メールアドレスが既に登録されています");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        return repo.save(user);
    }
}
