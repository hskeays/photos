package com.hskeays.hank.photos.controller;

import com.hskeays.hank.photos.service.PhotosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {

    private final PhotosService photosService;

    public UploadController(PhotosService photosService) {
        this.photosService = photosService;
    }

    @GetMapping("/upload")
    public String showUploadPage(Model model) {
        model.addAttribute("statusMessage", "");
        model.addAttribute("photos", photosService.get());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileupload") MultipartFile file, Model model) throws IOException {
        if (!file.isEmpty()) {
            photosService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            model.addAttribute("statusMessage", "File uploaded successfully!");
        } else {
            model.addAttribute("statusMessage", "No file selected!");
        }

        model.addAttribute("photos", photosService.get());
        return "upload";
    }

}
