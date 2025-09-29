package com.example.pib2.model.user.mappers;


import com.example.pib2.model.user.dto.UserDto;
import com.example.pib2.model.user.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setEnabled(userDto.isEnabled());
        return user;
    }
}
