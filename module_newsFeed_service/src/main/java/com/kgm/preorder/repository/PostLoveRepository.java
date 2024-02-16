package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.entity.Post_love;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLoveRepository extends JpaRepository<Post_love, Long> {
    List<Post_love> findByMemberIn(List<Member> followingMembers);
}
