package com.rabin.backend.controller;

import com.rabin.backend.dto.request.ForgotPasswordRequestDTO;
import com.rabin.backend.dto.request.ResetPasswordRequestDTO;
import com.rabin.backend.dto.response.GenericApiResponse;
import com.rabin.backend.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Password Management", description = "APIs for forgot and reset password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Operation(
            summary = "Send OTP to user email",
            description = "Generates an OTP and sends it to the user's registered email for password reset",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OTP sent successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":true,\"message\":\"OTP sent successfully\",\"data\":null,\"code\":200}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid email",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":false,\"message\":\"Invalid email\",\"data\":null,\"code\":400}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<GenericApiResponse<Object>> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        passwordResetService.sendOtp(request.getEmail());
        GenericApiResponse<Object> response = new GenericApiResponse<>(
                true,
                "OTP sent successfully",
                null,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Reset user password",
            description = "Resets the user's password using the provided OTP",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password reset successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":true,\"message\":\"Password reset successfully\",\"data\":null,\"code\":200}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid OTP or email",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = GenericApiResponse.class,
                                            example = "{\"success\":false,\"message\":\"Invalid OTP or email\",\"data\":null,\"code\":400}"
                                    )
                            )
                    )
            }
    )
    @PostMapping("/reset-password")
    public ResponseEntity<GenericApiResponse<Object>> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        passwordResetService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
        GenericApiResponse<Object> response = new GenericApiResponse<>(
                true,
                "Password reset successfully",
                null,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}
