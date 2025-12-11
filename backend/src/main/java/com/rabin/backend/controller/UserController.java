package com.rabin.backend.controller;

import com.rabin.backend.dto.request.UserLoginDTO;
import com.rabin.backend.dto.request.UserRequestDTO;
import com.rabin.backend.dto.response.GenericApiResponse;
import com.rabin.backend.dto.response.UserResponseDTO;
import com.rabin.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user registration and login")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with optional profile image",
            requestBody = @RequestBody(
                    description = "User registration details",
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = UserRequestDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":true,\"message\":\"User registered successfully\",\"data\":{\"id\":1,\"fullName\":\"Ritika Suwal\",\"email\":\"rikkypikky@example.com\",\"profileImageUrl\":null,\"userStatus\":\"ACTIVE\",\"createdAt\":\"2025-12-11T16:51:41\",\"updatedAt\":\"2025-12-11T16:51:41\"},\"code\":201}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":false,\"message\":\"Invalid input\",\"data\":null,\"code\":400}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<GenericApiResponse<UserResponseDTO>> register(@ModelAttribute UserRequestDTO request) {
        UserResponseDTO userResponse = userService.registerUserWithImage(request);
        GenericApiResponse<UserResponseDTO> response = new GenericApiResponse<>(
                true,
                "User registered successfully",
                userResponse,
                HttpStatus.CREATED.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "User login",
            description = "Authenticate a user using email and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":true,\"message\":\"Login successful\",\"data\":{\"id\":1,\"fullName\":\"Rabin Pyakurel\",\"email\":\"rabinstar137@example.com\",\"profileImageUrl\":null,\"userStatus\":\"ACTIVE\",\"createdAt\":\"2025-12-11T16:51:41\",\"updatedAt\":\"2025-12-11T16:51:41\"},\"code\":200}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid credentials",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":false,\"message\":\"Invalid credentials\",\"data\":null,\"code\":401}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<GenericApiResponse<UserResponseDTO>> login(@Valid @RequestBody UserLoginDTO loginInfo) {
        UserResponseDTO userResponse = userService.loginUser(loginInfo);
        GenericApiResponse<UserResponseDTO> response = new GenericApiResponse<>(
                true,
                "Login successful",
                userResponse,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
