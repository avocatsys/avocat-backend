package com.avocat.controller.password;

import com.avocat.controller.password.dto.EmailDto;
import com.avocat.controller.password.dto.ForgotPasswordDto;
import com.avocat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(path = "/v1/password/", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PasswordController {

    @Autowired
    private UserService userService;

    @PutMapping("/reset")
    public ResponseEntity<Void> reset(
            @RequestBody ForgotPasswordDto forgotPasswordDto, HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        userService.resetPassword(forgotPasswordDto, token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(
            @RequestBody EmailDto emailDto) {
        userService.sendLinkToRecoverPassword(emailDto.email());
        return ResponseEntity.noContent().build();
    }
}