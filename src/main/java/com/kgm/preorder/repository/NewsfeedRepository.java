package com.kgm.preorder.repository;

import com.kgm.preorder.Dto.NewsfeedDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NewsfeedRepository {
    public List<NewsfeedDTO> findByMemberIdOrderByCreatedAtDesc(String memberId) {
        // 여기서는 임시적으로 예시 데이터를 사용
        return List.of(
                new NewsfeedDTO(memberId, "post", "새로운 포스트를 작성했습니다.", LocalDateTime.now()),
                new NewsfeedDTO(memberId, "comment", "A님이 글에 댓글을 남겼습니다.", LocalDateTime.now()),
                new NewsfeedDTO(memberId, "like", "A님이 글을 좋아합니다.", LocalDateTime.now())
                // 필요한 만큼 데이터를 추가할 수 있음
        );
    }
}
