package com.hskeays.hank.photos.controller;

import com.hskeays.hank.photos.model.Photo;
import com.hskeays.hank.photos.service.PhotosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class PhotosController {

    private final PhotosService photosService;

    public PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }


    @GetMapping("/photos")
    public String photos(Model model) {
        Iterable<Photo> photos = photosService.get();
        model.addAttribute("photos", photos);
        model.addAttribute("name", "John Doe");
        return "photos";
    }

    @GetMapping("/photos/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) {
        Photo photo = photosService.getPhotoById(id);
        if (photo != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(photo.getContentType()))
                    .body(photo.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/photos/delete")
    public String deletePhotos(@RequestParam List<Integer> photoIds) {
        if (photoIds != null && !photoIds.isEmpty()) {
            for (Integer id : photoIds) {
                if (photosService.getPhotoById(id) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo with ID " + id + " not found");
                }
                photosService.deleteById(id);
            }
        }
        return "redirect:/photos";
    }
}
