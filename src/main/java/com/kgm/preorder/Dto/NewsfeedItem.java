package com.kgm.preorder.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class NewsfeedItem {

    private String Member;
    private String type;
    private String content;
    private LocalDateTime createdAt;

}