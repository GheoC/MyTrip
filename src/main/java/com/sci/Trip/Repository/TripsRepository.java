package com.sci.Trip.Repository;

import com.sci.Trip.Model.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TripsRepository extends CrudRepository<Trip, Integer > {


    @Query(value = "select * from trips where sharable=1", nativeQuery = true)
    Iterable<Trip> findAllSharableTrips();

    @Query(value = "select * from trips where user_id=?", nativeQuery = true)
    Iterable<Trip> findTripsByUserID(int userId);

    @Query(value = "select * from trips where trips_id=?", nativeQuery = true)
    Trip findTripByTripID(int tripId);


}