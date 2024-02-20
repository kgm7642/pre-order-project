package com.kgm.preorder.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MyPostActionDTO {

    private String Member;
    private String activityType;
    private String content;
    private LocalDateTime createdAt;

    public MyPostActionDTO() {

    }
}
