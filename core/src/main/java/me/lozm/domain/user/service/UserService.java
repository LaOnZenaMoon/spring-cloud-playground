package me.lozm.domain.user.service;

import me.lozm.domain.user.vo.UserCreateVo;

public interface UserService {

    UserCreateVo createUser(UserCreateVo userCreateVo);

}
