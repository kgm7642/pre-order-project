package com.kgm.preorder.controller;

import com.kgm.preorder.domain.Users;
import com.kgm.preorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        return "ok";
    }

    @PostMapping(value = "/signUp", consumes = "application/json")
    public String signUp(@RequestBody Users users) {
        userService.save(users);

        return "가입완료";
    }
}
