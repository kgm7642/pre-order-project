package com.kgm.preorder.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class NewPWResponseDTO {

    private String email;
    private String newPassword;

    public NewPWResponseDTO() {

    }
}
