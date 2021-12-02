package me.lozm.api;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.user.dto.UserCreateRequestDto;
import me.lozm.domain.user.dto.UserCreateResponseDto;
import me.lozm.domain.user.service.UserService;
import me.lozm.domain.user.vo.UserCreateVo;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Environment environment;
    private final UserService userService;


    @RequestMapping("health-check")
    public String healthCheck() {
        return environment.getProperty("health-check.message");
    }

    @PostMapping("users")
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateVo userCreateVo = mapStrictly(requestDto, UserCreateVo.class);
        UserCreateVo returnUserCreateVo = userService.createUser(userCreateVo);

        UserCreateResponseDto userCreateResponseDto = mapStrictly(returnUserCreateVo, UserCreateResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userCreateResponseDto);
    }

}
