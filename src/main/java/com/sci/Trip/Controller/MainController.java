package com.sci.Trip.Controller;

import com.sci.Trip.Model.Picture;
import com.sci.Trip.Model.Trip;
import com.sci.Trip.Model.User;
import com.sci.Trip.Service.PictureService;
import com.sci.Trip.Service.TripsService;
import com.sci.Trip.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    TripsService tripsService;

    @Autowired
    PictureService pictureService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getMainPage() {
        ModelAndView modelAndView = new ModelAndView();

        Iterable<Trip> tripsList = tripsService.getAllShaTrips();
        for (Trip trip:tripsList){
            trip.setPictureList((List<Picture>) pictureService.getAllPicturesByTripId(trip.getTripId()));

            System.out.println(trip.getTripId()+", "+trip.getPictureName());
            trip.getPictureList().forEach(picture -> System.out.println(picture.getPictureBase64()));
        }
        modelAndView.addObject("trips",tripsList);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            User user = userService.findUser(auth.getName());

            modelAndView.addObject("userName", "Welcome, " +
                    user.getName() + " " + user.getLastName());
        }
        modelAndView.setViewName("main");
        return modelAndView;

    }

}
