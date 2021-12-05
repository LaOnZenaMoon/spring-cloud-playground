package me.lozm.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private final Environment environment;


    @GetMapping("welcome")
    public String welcomeAdminService() {
        return "Welcome to Admin Service";
    }

    @GetMapping("header-test")
    public String testHeader(@RequestHeader("admin-request") String requestHeader) {
        log.info("requestHeader = " + requestHeader);
        return "Welcome to Admin Service";
    }

    @GetMapping("check")
    public String check(HttpServletRequest httpServletRequest) {
        log.info("Server port = {}", httpServletRequest.getServerPort());
        return String.format("Admin Service Information: Port(%s)", environment.getProperty("local.server.port"));
    }

}
