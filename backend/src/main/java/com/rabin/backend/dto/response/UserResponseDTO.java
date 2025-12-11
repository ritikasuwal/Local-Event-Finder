package com.rabin.backend.dto.response;

import com.rabin.backend.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {

    private String fullName;

    private String email;

    private String token;

    private String profileImageUrl;

    private UserStatus userStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
