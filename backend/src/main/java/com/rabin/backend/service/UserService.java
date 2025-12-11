package com.rabin.backend.service;

import com.rabin.backend.dto.request.UserLoginDTO;
import com.rabin.backend.dto.request.UserRequestDTO;
import com.rabin.backend.dto.response.UserResponseDTO;
import com.rabin.backend.enums.UserStatus;
import com.rabin.backend.model.User;
import com.rabin.backend.repo.UserRepository;
import com.rabin.backend.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponseDTO registerUserWithImage(UserRequestDTO request){

        if(!request.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("Invalid email format");
        }

        if(request.getPassword().length()<8){
            throw new IllegalArgumentException("Password must be at least 8 character long");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            String imageUrl = FileUtil.saveFile(request.getProfileImage(), "users");
            user.setProfileImageUrl(imageUrl);
        }

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    public UserResponseDTO loginUser(UserLoginDTO loginDTO){
        Optional<User> optionalUser = userRepository.findByEmail(loginDTO.getEmail());
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Invalid credentials");
        }

        User user = optionalUser.get();
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        if(user.getUserStatus()!= UserStatus.ACTIVE){
            throw new RuntimeException("User is suspended");
        }

        return mapToResponse(user);
    }

    private UserResponseDTO mapToResponse(User user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        dto.setUserStatus(user.getUserStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
