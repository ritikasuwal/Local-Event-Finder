package com.rabin.backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserRequestDTO {

    private String fullName;
    private String email;
    private String password;

    @Schema(description = "Profile image of the user")
    private MultipartFile profileImage;
}
