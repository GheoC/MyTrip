package com.sci.Trip.Service;

import com.sci.Trip.Model.Picture;
import com.sci.Trip.Repository.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class PictureService {

    @Autowired
    private PicturesRepository picturesRepository;

    public void storePicture(int tripsId, MultipartFile file) {
        Picture picture = null;
        String picBase64;
        try {
            picBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            System.out.println(picBase64);
            picture = new Picture(tripsId, file.getBytes());
            picture.setPictureBase64(picBase64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        picturesRepository.save(picture);
    }


    public Iterable<Picture> getAllPicturesByTripId(int trip_id) {
        return picturesRepository.findPicturesByTripID(trip_id);
    }

    public void removePictureByTripId(int tripId){
        picturesRepository.deletePicturesByTripID(tripId);
    }
}
