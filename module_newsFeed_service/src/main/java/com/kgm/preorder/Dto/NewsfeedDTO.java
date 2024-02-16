package com.kgm.preorder.Dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class NewsfeedDTO {

    private String Member;
    private String activityType;
    private String content;
    private LocalDateTime createdAt;

    public NewsfeedDTO() {

    }
}