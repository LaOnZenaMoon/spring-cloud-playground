package me.lozm.domain.user.service;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.user.entity.User;
import me.lozm.domain.user.repository.UserRepository;
import me.lozm.domain.user.vo.UserCreateVo;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserCreateVo createUser(UserCreateVo userCreateVo) {
        userCreateVo.setUserId(UUID.randomUUID().toString());

        User user = mapStrictly(userCreateVo, User.class);
        user.encryptPassword("encrypted_password");
        userRepository.save(user);

        return null;
    }

}
