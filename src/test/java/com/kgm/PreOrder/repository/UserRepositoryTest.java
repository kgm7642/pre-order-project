package com.kgm.PreOrder.repository;

import com.kgm.PreOrder.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired UserRepository userRepository;

    @Test
    @Transactional
    public void testUser() throws Exception {
        User user = new User();
        user.setName("kkkkk");

        Long saveId = userRepository.save(user);
        User findUser = userRepository.find(saveId);

        if (findUser.getId().equals(user.getId())) {
            System.out.println("통과");
        }
    }
}