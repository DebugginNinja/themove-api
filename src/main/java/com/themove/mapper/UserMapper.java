package com.themove.mapper;

import com.themove.dto.UserProfileDto;
import com.themove.model.User;

public class UserMapper {

    private UserMapper() {
    }

    public static UserProfileDto toDto(User user) {

        UserProfileDto dto = new UserProfileDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setBio(user.getBio());
        dto.setProfileImageUrl(user.getProfileImageUrl());

        return dto;
    }
}