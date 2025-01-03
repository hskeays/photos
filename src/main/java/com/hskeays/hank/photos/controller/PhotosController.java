package com.hskeays.hank.photos.controller;

import com.hskeays.hank.photos.model.Photo;
import com.hskeays.hank.photos.service.PhotosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class PhotosController {

    private final PhotosService photosService;

    public PhotosController(PhotosService photosService) {
        this.photosService = photosService;
    }


    @GetMapping("/photos")
    public String photos(Model model) {
        Iterable<Photo> photos = photosService.get(); // Get all photos
        model.addAttribute("photos", photos); // Add photos to model
        model.addAttribute("name", "John Doe");
        return "photos"; // Render the photos.html template
    }

    @GetMapping("/photos/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) {
        Photo photo = photosService.get(id);
        if (photo != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(photo.getContentType()))
                    .body(photo.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a photo
    @DeleteMapping("/photos/{id}")
    public void delete(@PathVariable Integer id) {
        if (photosService.get(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        photosService.remove(id);
    }
}
