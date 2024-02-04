package com.kgm.preorder.repository;

import com.kgm.preorder.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
}
