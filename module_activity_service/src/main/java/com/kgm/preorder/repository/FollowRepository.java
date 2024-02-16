package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository  extends JpaRepository<Follow, Long> {


}
