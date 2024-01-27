package com.kgm.preorder.repository;

import com.kgm.preorder.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

}
