package com.hskeays.hank.photos.repsository;

import com.hskeays.hank.photos.model.Photo;
import org.springframework.data.repository.CrudRepository;


public interface PhotosRepository extends CrudRepository<Photo, Integer> {

}
