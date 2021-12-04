package me.lozm.api.controller;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.user.dto.UserCreateRequestDto;
import me.lozm.domain.user.dto.UserCreateResponseDto;
import me.lozm.api.service.UserService;
import me.lozm.domain.user.dto.UserInfoResponseDto;
import me.lozm.domain.user.vo.UserCreateVo;
import me.lozm.domain.user.vo.UserInfoVo;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@RequestMapping("user-service")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final Environment environment;
    private final UserService userService;


    @GetMapping("health-check")
    public String healthCheck() {
        return String.format("User service is available on PORT %s", environment.getProperty("local.server.port"));
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<UserInfoResponseDto> getUserDetail(@PathVariable("userId") String userId) {
        UserInfoVo userInfoVo = userService.getUserDetail(userId);

        UserInfoResponseDto userInfoResponseDto = mapStrictly(userInfoVo, UserInfoResponseDto.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfoResponseDto);
    }

    @GetMapping("users")
    public ResponseEntity<List<UserInfoResponseDto>> getUserList() {
        List<UserInfoVo> userList = userService.getUserList();

        List<UserInfoResponseDto> responseList = userList.stream()
                .map(userInfoVo -> mapStrictly(userInfoVo, UserInfoResponseDto.class))
                .collect(toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @PostMapping("users")
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto requestDto) {
        UserCreateVo userCreateVo = mapStrictly(requestDto, UserCreateVo.class);
        UserCreateVo responseUserCreateVo = userService.createUser(userCreateVo);

        UserCreateResponseDto userCreateResponseDto = mapStrictly(responseUserCreateVo, UserCreateResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userCreateResponseDto);
    }

}
