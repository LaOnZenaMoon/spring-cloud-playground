package me.lozm.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.lozm.api.service.UserService;
import me.lozm.domain.user.dto.UserLoginRequestDto;
import me.lozm.domain.user.vo.UserInfoVo;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final Environment environment;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((User) authResult.getPrincipal()).getUsername();
        log.info(format("Authenticate successfully: Login username -> %s", email));

        //TODO 로그인 성공 이후, Token 응답 처리
        UserInfoVo userInfoVo = userService.findByEmail(email);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error(format("Authenticate unsuccessfully: message -> %s", failed.getMessage()));
    }

}
