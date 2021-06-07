package com.sci.Trip.Controller;

import com.sci.Trip.Model.Picture;
import com.sci.Trip.Model.Trip;
import com.sci.Trip.Model.User;
import com.sci.Trip.Service.PictureService;
import com.sci.Trip.Service.TripsService;
import com.sci.Trip.Service.UserService;
import com.sci.Trip.Validator.TripValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class TripsController {

    TripsService tripsService;
    UserService userService;
    PictureService pictureService;
    TripValidator tripValidator;

    @Autowired
    public void setTripsService(TripsService tripsService) {
        this.tripsService = tripsService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setPictureService(PictureService pictureService) {
        this.pictureService = pictureService;
    }
    @Autowired
    public void setTripValidator(TripValidator tripValidator) {
        this.tripValidator = tripValidator;
    }

    // Adding a trip
    @GetMapping(path = "/addNewTrip")
    public String getAddNewTripPage(Model model) {
        model.addAttribute("trip", new Trip());
        return "addNewTrip";
    }
    @PostMapping(value = "/addNewTrip")
    public String addNewTrip(@ModelAttribute("trip") Trip trip, @RequestParam("files") List<MultipartFile> files, BindingResult bindingResult) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        int userId = userService.findUser(currentPrincipalName).getId();
        trip.setUserId(userId);
        trip.setPictureName("deprecated");

        tripValidator.validate(trip,bindingResult);

        if (bindingResult.hasErrors()) {
            return "addNewTrip";
        }

        tripsService.saveTrip(trip);
        for (MultipartFile file : files) {
            pictureService.storePicture(trip.getTripId(), file);
        }

        return "redirect:/myTrips";
    }

    //Deprecated
    @GetMapping("/upload")
    public String UploadPage(Model model) {
        return "upload";
    }

    //Deprecated...
    //I let this code in because at some point in the development of this app,
    // it also saved the picture files on the disk
    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files, HttpSession session) {

        StringBuilder fileNames = new StringBuilder();

        Trip trip = (Trip) session.getAttribute("trips");
//        String uploadDirectory = System.getProperty("user.dir") + "uploads" + "\\" + trip.getUserId() + "\\" + trip.getTripId();
//        System.out.println(uploadDirectory);
//        new File(uploadDirectory).mkdirs();

        for (MultipartFile file : files) {
//            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
//            fileNames.append(file.getOriginalFilename() + ", ");

            pictureService.storePicture(trip.getTripId(), file);

//            try {
//                Files.write(fileNameAndPath, file.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        model.addAttribute("msg", "Successfully uploaded files" + fileNames.toString());
        return "uploadStatusView";
    }

    //Viewing trips for the user who is logged in
    @GetMapping("/myTrips")
    public ModelAndView myTrips() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        int userId = userService.findUser(currentPrincipalName).getId();
        User user = userService.findUser(authentication.getName());
        modelAndView.addObject("userName", "Welcome, " +
                user.getName() + " " + user.getLastName());

        modelAndView.addObject("trips2", tripsService.getMyTrips(userId));
        return modelAndView;
    }


    //Viewing a selected trip
    @PostMapping(value = "/viewTrip")
    public ModelAndView viewTripPage(@RequestParam(name = "selectedTripId") Integer tripId) {
        ModelAndView modelAndView = new ModelAndView();
        Trip currentTrip = tripsService.getTripByTripId(tripId);
        currentTrip.setPictureList((List<Picture>) pictureService.getAllPicturesByTripId(tripId));
        modelAndView.addObject("selectedTrip", currentTrip);
        return modelAndView;
    }

    //Deleting a selected trip
    @PostMapping(value = "/delete")
    public String removeAd(@RequestParam(name = "selectedTripId") Integer tripId)
    {
        Trip trip = tripsService.getTripByTripId(tripId);
        System.out.println(trip.getTripId());
        System.out.println(trip.getTripName());
        System.out.println(trip.getDescription());

        pictureService.removePictureByTripId(tripId);
        tripsService.removeTripByTripId(tripId);

        return "redirect:/myTrips";
    }

    //Editing a selected trip

    @GetMapping(value = "/edit")
    public String editTrip(@RequestParam(name = "selectedTripId") int tripId, Model model) {

       Trip trip = tripsService.getTripByTripId(tripId);

        model.addAttribute("trip",trip);

        return "editTrip";
    }

    @PostMapping(value = "/edit")
    public String saveEditedTrip(@ModelAttribute("trip") Trip trip, Model model, BindingResult bindingResult) {

        Trip updateTrip = tripsService.getTripByTripId(trip.getTripId());

        updateTrip.setTripName(trip.getTripName());
        updateTrip.setDescription(trip.getDescription());

        tripValidator.validate(updateTrip,bindingResult);
        if (bindingResult.hasErrors()) {
            return "editTrip";
        }

        tripsService.save(updateTrip);
        model.addAttribute("trip", updateTrip);

        return "redirect:/myTrips";
    }


}
