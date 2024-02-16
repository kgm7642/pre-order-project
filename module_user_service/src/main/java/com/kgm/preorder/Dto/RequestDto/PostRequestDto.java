package com.kgm.preorder.Dto.RequestDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostRequestDto {

    private Long MemberId;
    private String content;

    public PostRequestDto() {
    }
}
