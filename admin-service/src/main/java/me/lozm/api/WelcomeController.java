package me.lozm.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("admin-service")
@RestController
public class WelcomeController {

    @GetMapping("welcome")
    public String welcomeAdminService() {
        return "Welcome to Admin Service";
    }

    @GetMapping("header-test")
    public String testHeader(@RequestHeader("admin-request") String requestHeader) {
        System.out.println("requestHeader = " + requestHeader);
        return "Welcome to Admin Service";
    }

}
