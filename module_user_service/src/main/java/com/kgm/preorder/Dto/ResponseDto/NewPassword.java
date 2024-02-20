package com.kgm.preorder.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class NewPassword {

    private String email;
    private String newPassword;

    public NewPassword() {

    }
}
