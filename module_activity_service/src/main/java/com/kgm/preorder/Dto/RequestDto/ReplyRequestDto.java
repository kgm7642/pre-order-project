package com.kgm.preorder.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplyRequestDto {

    private Long PostId;
    private String comment;

    public ReplyRequestDto() {
    }
}
