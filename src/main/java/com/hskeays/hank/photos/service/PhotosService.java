package com.hskeays.hank.photos.service;

import com.hskeays.hank.photos.model.Photo;
import com.hskeays.hank.photos.repsository.PhotosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotosService {

    private final PhotosRepository photosRepository;

    public PhotosService(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public Iterable<Photo> get() {
        return photosRepository.findAll();
    }

    public Photo getPhotoById(Integer id) {
        return photosRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        photosRepository.deleteById(id);
    }

    public void deleteByIds(List<Integer> ids) {
        for (int id : ids) {
            photosRepository.deleteById(id);
        }
    }

    public Photo save(String fileName, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setContentType(contentType);
        photo.setData(data);
        photosRepository.save(photo);
        return photo;
    }
}
