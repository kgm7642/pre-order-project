package com.kgm.PreOrder.controller;

import com.kgm.PreOrder.domain.User;
import com.kgm.PreOrder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;

    @PostMapping("/user/login")
    public void userLogin(@RequestBody User user) {
        userRepository.save(user);
    }
}
