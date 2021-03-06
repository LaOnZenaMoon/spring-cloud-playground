package me.lozm.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.api.client.OrderServiceClient;
import me.lozm.domain.order.dto.OrderInfoResponseDto;
import me.lozm.domain.user.entity.User;
import me.lozm.domain.user.repository.UserRepository;
import me.lozm.domain.user.vo.OrderInfoVo;
import me.lozm.domain.user.vo.UserCreateVo;
import me.lozm.domain.user.vo.UserInfoVo;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static me.lozm.global.utils.ModelMapperUtils.mapStrictly;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    //    private final Environment environment;
//    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;


    @Override
    public UserCreateVo createUser(UserCreateVo userCreateVo) {
        userCreateVo.setUserId(UUID.randomUUID().toString());

        User user = mapStrictly(userCreateVo, User.class);
        user.encryptPassword(passwordEncoder.encode(userCreateVo.getPassword()));
        userRepository.save(user);

        return userCreateVo;
    }

    @Override
    public UserInfoVo getUserDetail(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException(format("사용자 정보가 존재하지 않습니다. 사용자 ID: %s", userId)));
        UserInfoVo userInfoVo = mapStrictly(user, UserInfoVo.class);

//        String usersOrdersApiUrl = environment.getProperty("microservice.order-service.url") + format(environment.getProperty("microservice.order-service.get-users-orders"), userId);
//        ResponseEntity<List<OrderInfoResponseDto>> orderListResponse = restTemplate.exchange(usersOrdersApiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderInfoResponseDto>>() {});
//        List<OrderInfoVo> orderList = orderListResponse.getBody()
//                .stream()
//                .map(orderInfoResponseDto -> mapStrictly(orderInfoResponseDto, OrderInfoVo.class))
//                .collect(toList());


//        List<OrderInfoResponseDto> responseList = orderServiceClient.getOrders(userId);

        log.info("Before call orders microservice");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        List<OrderInfoResponseDto> responseList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());
        log.info("After call orders microservice");

        List<OrderInfoVo> orderList = responseList
                .stream()
                .map(orderInfoResponseDto -> mapStrictly(orderInfoResponseDto, OrderInfoVo.class))
                .collect(toList());

        userInfoVo.setOrders(orderList);
        return userInfoVo;
    }

    @Override
    public List<UserInfoVo> getUserList() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapStrictly(user, UserInfoVo.class))
                .collect(toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("사용자 정보가 존재하지 않습니다. 사용자 이메일: %s", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>()
        );
    }

    @Override
    public UserInfoVo findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(format("사용자 정보가 존재하지 않습니다. 사용자 이메일: %s", email)));

        return mapStrictly(user, UserInfoVo.class);
    }

}
