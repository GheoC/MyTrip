package com.sci.Trip.Service;

import com.sci.Trip.Model.Trip;
import com.sci.Trip.Repository.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

    TripsRepository tripsRepository;
    @Autowired
    public void setTripsRepository(TripsRepository tripsRepository) {
        this.tripsRepository = tripsRepository;
    }

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

    public Trip getTripByTripIdAndUserId(int tripId,int userID)
    {
        return tripsRepository.findByTripIdAndUserId(tripId, userID);
    }

    public void removeTripByTripId(int tripId){
        tripsRepository.deleteById(tripId);
    }

    public void save(Trip trip) {
        tripsRepository.save(trip);
    }

}
