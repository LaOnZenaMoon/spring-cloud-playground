package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import me.lozm.domain.user.entity.User;
import me.lozm.domain.user.repository.UserRepository;
import me.lozm.domain.user.vo.UserCreateVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserCreateVo createUser(UserCreateVo userCreateVo) {
        userCreateVo.setUserId(UUID.randomUUID().toString());

        User user = mapStrictly(userCreateVo, User.class);
        user.encryptPassword(passwordEncoder.encode(userCreateVo.getPassword()));
        userRepository.save(user);

        return userCreateVo;
    }

}
