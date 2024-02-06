package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberIn(List<Member> followingMembers);
}
