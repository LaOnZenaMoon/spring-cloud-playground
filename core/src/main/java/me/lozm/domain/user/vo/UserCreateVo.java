package me.lozm.domain.user.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class UserCreateVo {

    private String email;
    @Setter
    private String userId;
    private String name;
    private String password;
    private String encryptedPassword;
    private LocalDateTime createdDateTime;

}
