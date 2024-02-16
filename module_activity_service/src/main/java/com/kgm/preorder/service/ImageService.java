package com.kgm.preorder.service;

import com.kgm.preorder.entity.Member;
import com.kgm.preorder.entity.MemberImage;
import com.kgm.preorder.repository.MemberRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    @Autowired
    private MemberRepository memberRepository;

    @Value("${image.upload.directory}")
    private String imageUploadDirectory; // application.properties 또는 application.yml에 설정된 디렉토리 경로

    public String uploadImage(MultipartFile image) throws IOException {

        // 이미지 저장 및 이미지를 업로드할 디렉토리가 존재하지 않으면 생성
        File uploadDir = new File(imageUploadDirectory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String filePath = imageUploadDirectory + File.separator + fileName;

        // 이미지를 업로드
        image.transferTo(new File(filePath));

        return fileName;
    }

    public String updateImage(Long memberId, MultipartFile newImage) throws IOException, NotFoundException {
        // 기존 이미지 삭제
        deleteImage(memberId);

        // 새로운 이미지 업로드 및 파일 이름 반환
        return uploadImage(newImage);
    }

    public void deleteImage(Long memberId) throws NotFoundException {
        // 기존 이미지 파일 삭제
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found with ID: " + memberId));

        MemberImage image = member.getImage();
        log.info("이미지서비스의 image : {}", image);
        if (image != null) {
            String imagePath = image.getImagePath();
            String filePath = imageUploadDirectory + File.separator + imagePath;
            log.info("imagePath : {}", imagePath);
            log.info("filePath : {}", filePath);

            File imageFile = new File(filePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }
}