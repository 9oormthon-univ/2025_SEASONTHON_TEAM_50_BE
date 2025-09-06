package com.ready.weddingplanner.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @Data
    @NoArgsConstructor
    public static class UpdateRequest {
        private String name;
        private String phone;
        private String password;
    }
}
