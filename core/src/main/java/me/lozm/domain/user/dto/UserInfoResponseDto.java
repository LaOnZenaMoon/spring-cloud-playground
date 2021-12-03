package me.lozm.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoResponseDto {

    private String email;
    private String name;
    private String userId;
    private List<OrderInfoResponseDto> orders;

}
