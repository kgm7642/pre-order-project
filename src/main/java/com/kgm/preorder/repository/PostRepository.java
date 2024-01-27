package com.kgm.preorder.repository;

import com.kgm.preorder.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
