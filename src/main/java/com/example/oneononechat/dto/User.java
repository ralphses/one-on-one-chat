package com.example.oneononechat.dto;

import com.example.oneononechat.enums.UserStatus;

public record User(
        String nickName,
        String fullName,
        UserStatus status
) {
}
