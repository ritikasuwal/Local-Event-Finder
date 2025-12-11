package com.rabin.backend.service;

import com.rabin.backend.dto.request.UserRequestDTO;
import com.rabin.backend.model.PasswordResetToken;
import com.rabin.backend.model.User;
import com.rabin.backend.repo.PasswordResetTokenRepository;
import com.rabin.backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final int OTP_OPERATION_MINUTES =10;

    public void sendOtp(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("User not found with email"));

        String otp = String.format("%06d",new Random().nextInt(999999));

        PasswordResetToken token = tokenRepository.findByUser(user).orElse(new PasswordResetToken());
        token.setUser(user);
        token.setOtp(otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password reset request");
        message.setText("Your OTP is: "+otp+" .Use it within 10 minutes");
        mailSender.send(message);
    }

    public void resetPassword(String email, String otp, String newPassword){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("User not found with email"));

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElseThrow(()->new IllegalArgumentException("OTP not found, request a new one"));

        if(!token.getOtp().equals(otp)){
            throw new IllegalArgumentException("Invalid OTP");
        }

        if(token.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("OTP has expired, request a new one");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(token);
    }
}
