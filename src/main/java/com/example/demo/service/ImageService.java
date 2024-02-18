package com.example.demo.service;

import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${upload.path}") // application.properties 또는 application.yml에서 경로를 읽어옴
    private String uploadPath;

    public void uploadImage(MultipartFile file) throws IOException {
        // 이미지 파일을 웹 서버에 저장하고 파일 경로를 얻는 코드
        String filePath = saveImageToFileSystem(file);

        // 이미지 엔터티 생성 및 저장
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setFilePath(filePath);
        imageRepository.save(image);
    }

    private String saveImageToFileSystem(MultipartFile file) throws IOException {
        // 파일 저장 경로
        String savePath = uploadPath + File.separator + file.getOriginalFilename();

        // 이미지 파일을 저장
        Path path = Paths.get(savePath);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return savePath;
    }
}
