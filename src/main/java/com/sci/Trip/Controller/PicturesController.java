package com.sci.Trip.Controller;

import com.sci.Trip.Model.Picture;
import com.sci.Trip.Repository.PicturesRepository;
import com.sci.Trip.Repository.TripsRepository;
import com.sci.Trip.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PicturesController {

    PicturesRepository picturesRepository;
    UserService userService;
    TripsRepository tripsRepository;

    @Autowired
    public void setPicturesRepository(PicturesRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setTripsRepository(TripsRepository tripsRepository) {
        this.tripsRepository = tripsRepository;
    }

    //just for fun :)
    @GetMapping("/pic")
    public ModelAndView getAllImages(){
        ModelAndView modelAndView=new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        int userId = userService.findUser(currentPrincipalName).getId();

        List<Integer> tripsIDs = tripsRepository.findTripsIdByUserId(userId);

        List<Picture> pictures = new ArrayList<>();
        for (Integer tripID:tripsIDs){
            Iterable<Picture> pictureIterable = picturesRepository.findPicturesByTripID(tripID);
            for (Picture picture:pictureIterable)
            {
                pictures.add(picture);
            }
        }

        return modelAndView.addObject("picturesList", pictures);
    }
}
