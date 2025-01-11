package com.shivam.personal_finance_manager.service;

import com.shivam.personal_finance_manager.dto.LoginDTO;
import com.shivam.personal_finance_manager.dto.UserDTO;
import com.shivam.personal_finance_manager.entity.User;
import com.shivam.personal_finance_manager.repository.UserRepository;
import com.shivam.personal_finance_manager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(UserDTO userDTO) throws Exception {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new Exception("Email already registered");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    public String login(LoginDTO loginDTO) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if (!optionalUser.isPresent() || !passwordEncoder.matches(loginDTO.getPassword(), optionalUser.get().getPassword())) {
            throw new Exception("Invalid email or password");
        }
        return jwtUtil.generateToken(optionalUser.get().getEmail());
    }
}