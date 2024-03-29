package com.kgm.preorder.Dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class UpdateRequestDTO {
    private String newName;
    private String newComment;
    private MultipartFile newImage;

    public UpdateRequestDTO() {

    }
}
