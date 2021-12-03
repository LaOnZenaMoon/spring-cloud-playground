package me.lozm.api.service;

import me.lozm.domain.user.vo.UserCreateVo;
import me.lozm.domain.user.vo.UserInfoVo;

import java.util.List;

public interface UserService {

    UserCreateVo createUser(UserCreateVo userCreateVo);

    UserInfoVo getUserDetail(String userId);

    List<UserInfoVo> getUserList();

}
