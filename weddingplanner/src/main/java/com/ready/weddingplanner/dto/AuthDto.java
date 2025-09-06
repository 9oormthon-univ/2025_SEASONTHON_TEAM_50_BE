package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class AuthDto {

    @Data
    @NoArgsConstructor
    public static class SignUpRequest {
        private String email;
        private String password;
        private String name;
        private String phone;
        private User.Role role;

        // Optional fields for GROOM/BRIDE roles
        private String partnerEmail;
        private LocalDate weddingDate;
        private Long budgetAmount;
    }

    @Data
    @NoArgsConstructor
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @NoArgsConstructor
    public static class UserResponse {
        private Long id;
        private String email;
        private String name;
        private String phone;
        private User.Role role;

        public UserResponse(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.name = user.getName();
            this.phone = user.getPhone();
            this.role = user.getRole();
        }
    }
}
