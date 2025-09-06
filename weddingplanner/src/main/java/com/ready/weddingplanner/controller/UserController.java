package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.User;
import com.ready.weddingplanner.dto.AuthDto;
import com.ready.weddingplanner.dto.UserDto;
import com.ready.weddingplanner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<AuthDto.UserResponse> getMe() {
        User user = userService.getMe();
        return ResponseEntity.ok(new AuthDto.UserResponse(user));
    }

    @PutMapping
    public ResponseEntity<Void> updateMe(@RequestBody UserDto.UpdateRequest request) {
        userService.updateMe(request);
        return ResponseEntity.ok().build();
    }
}
