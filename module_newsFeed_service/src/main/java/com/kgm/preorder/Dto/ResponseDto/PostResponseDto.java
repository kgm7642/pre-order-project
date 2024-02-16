package com.kgm.preorder.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class PostResponseDto {

    private Long id;
    private String content;
    private LocalDateTime date;

    public PostResponseDto() {
    }
}
