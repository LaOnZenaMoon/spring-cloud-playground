package me.lozm.domain.user.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserInfoVo {

    private String email;
    private String userId;
    private String name;
    private LocalDateTime createdDateTime;

    @Setter
    private List<OrderInfoVo> orders;

}
