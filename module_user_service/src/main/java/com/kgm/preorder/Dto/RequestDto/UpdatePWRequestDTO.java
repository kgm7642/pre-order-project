package com.kgm.preorder.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.Update;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePWRequestDTO {
    private String newPW;

    public UpdatePWRequestDTO() {

    }
}
