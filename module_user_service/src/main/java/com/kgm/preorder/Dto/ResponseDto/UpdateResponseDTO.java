package com.kgm.preorder.Dto.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UpdateResponseDTO {
    private String newName;
    private String newComment;

    public UpdateResponseDTO() {

    }
}
