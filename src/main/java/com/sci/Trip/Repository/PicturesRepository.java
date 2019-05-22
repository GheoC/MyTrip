package com.sci.Trip.Repository;

import com.sci.Trip.Model.Picture;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PicturesRepository extends CrudRepository<Picture, Integer> {

    @Query(value = "select * from pictures where trips_id=?", nativeQuery = true)
    Iterable<Picture> findPicturesByTripID(int tripId);

    @Modifying
    @Transactional
    @Query(value = "delete from pictures where trips_id=?", nativeQuery = true)
    void deletePicturesByTripID(int tripId);



}
