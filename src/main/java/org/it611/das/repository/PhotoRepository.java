package org.it611.das.repository;

import org.it611.das.domain.Photo;
import org.it611.das.domain.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}
