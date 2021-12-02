package me.lozm.domain.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserCreateResponseDto {

    private String email;
    private String userId;
    private String name;

}
