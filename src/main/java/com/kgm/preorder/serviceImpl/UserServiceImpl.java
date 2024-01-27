package com.kgm.preorder.serviceImpl;

import com.kgm.preorder.domain.Users;
import com.kgm.preorder.repository.UserRepository;
import com.kgm.preorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(Users users) {
        userRepository.save(users);
    }
}
