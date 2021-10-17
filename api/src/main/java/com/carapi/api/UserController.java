package com.carapi.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    //as infos que retorna vem das credenciais
    @GetMapping("/user")
    public UserDetails userInfo(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

}