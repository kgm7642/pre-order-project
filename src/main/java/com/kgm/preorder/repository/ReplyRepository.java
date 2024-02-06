package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
    List<Reply> findByMemberIn(List<Member> followingMembers);
}
