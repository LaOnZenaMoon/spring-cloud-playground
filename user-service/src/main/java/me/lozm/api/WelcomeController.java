package me.lozm.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user-service")
@RestController
public class WelcomeController {

    @GetMapping("welcome")
    public String welcomeUserService() {
        return "Welcome to User Service";
    }

    @GetMapping("header-test")
    public String testHeader(@RequestHeader("user-request") String requestHeader) {
        System.out.println("requestHeader = " + requestHeader);
        return "Welcome to User Service";
    }

}
