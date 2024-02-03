package com.kgm.preorder.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class FollowRequest {

    private Long followerId;

    private Long followingId;

    public FollowRequest() {
    }
}
