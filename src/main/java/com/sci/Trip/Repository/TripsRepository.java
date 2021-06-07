package com.sci.Trip.Repository;

import com.sci.Trip.Model.Trip;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TripsRepository extends CrudRepository<Trip, Integer > {


    @Query(value = "select * from trips where sharable=1", nativeQuery = true)
    Iterable<Trip> findAllSharableTrips();

    @Query(value = "select * from trips where user_id=?", nativeQuery = true)
    Iterable<Trip> findTripsByUserID(int userId);

    @Query(value = "select * from trips where trips_id=?", nativeQuery = true)
    Trip findTripByTripID(int tripId);

    @Query(value = "select trips_id from trips where user_id=?", nativeQuery = true)
    List<Integer> findTripsIdByUserId(int userID);


    @Query(value = "SELECT * FROM holidays WHERE id =:value1 AND user_id =:value2", nativeQuery = true)
    Trip findByTripIdAndUserId(@Param("value1") int tripId, @Param("value2") int userId);



    

}