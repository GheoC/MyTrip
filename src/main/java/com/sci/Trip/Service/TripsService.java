package com.sci.Trip.Service;

import com.sci.Trip.Model.Trip;
import com.sci.Trip.Repository.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

    @Autowired
    TripsRepository tripsRepository;

//    private Trip findTripByName(String tripName){
//
//        return tripsRepository.findByName(tripName);
//    }

    public void saveTrip(Trip trips){
        tripsRepository.save(trips);
    }

    public Iterable<Trip> getAllShaTrips(){
        return tripsRepository.findAllSharableTrips();
    }

    public Iterable<Trip> getAllTrips(){
        return tripsRepository.findAll();
    }

    public Iterable<Trip> getMyTrips(int userId){
        return tripsRepository.findTripsByUserID(userId);
    }

    public Trip getTripByTripId(int tripId){
        return tripsRepository.findTripByTripID(tripId);
    }

    public void removeTripByTripId(int tripId){
        tripsRepository.deleteById(tripId);
    }


}
