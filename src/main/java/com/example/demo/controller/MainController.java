package com.example.demo.controller;

import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {

    private final ImageService imageService;

    @Autowired
    public MainController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/main")
    String mainPage(){
        return "main";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile file) {
        String message;
        if(file.isEmpty()){
            message = "파일을 선택해주세요.";
            return message;
        }

        try {
            imageService.uploadImage(file);
            message = "파일 업로드에 성공했습니다.";
        } catch (IOException e) {
            message = "파일 업로드에 실패했습니다.";
        }

        return message;
    }
}
