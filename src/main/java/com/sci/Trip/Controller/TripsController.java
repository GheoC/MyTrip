package com.sci.Trip.Controller;

import com.sci.Trip.Model.Picture;
import com.sci.Trip.Model.Trip;
import com.sci.Trip.Model.User;
import com.sci.Trip.Service.PictureService;
import com.sci.Trip.Service.TripsService;
import com.sci.Trip.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    TripsService tripsService;

    @Autowired
    UserService userService;

    @Autowired
    PictureService pictureService;


    @GetMapping(path = "/addNewTrip")
    public String getAddNewTripPage(Model model){
        model.addAttribute("trips",new Trip());
        return "addNewTrip";
    }

    @RequestMapping(value = "/addNewTrip", method = RequestMethod.POST)
    public String addNewTrip(@Valid Trip trip, BindingResult bindingResult, HttpSession session){

        if(bindingResult.hasErrors()){
            return "addNewTrip";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        int userId = userService.findUser(currentPrincipalName).getId();

        trip.setUserId(userId);
        trip.setPictureName("blablabla");

        System.out.println("user id:"+userId);
        tripsService.saveTrip(trip);
        System.out.println("trips id:"+trip.getTripId());
        session.setAttribute("trips", trip);
        return "upload";
    }

    @GetMapping("/upload")
    public String UploadPage(Model model) {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("files") MultipartFile[] files, HttpSession session) {

        StringBuilder fileNames = new StringBuilder();

        Trip trip = (Trip) session.getAttribute("trips");
        String uploadDirectory = System.getProperty("user.dir") +"uploads"+"\\"+trip.getUserId()+"\\"+trip.getTripId();

        System.out.println(uploadDirectory);

        new File(uploadDirectory).mkdirs();

        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + ", ");

            pictureService.storePicture(trip.getTripId(),file);

            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("msg", "Successfully uploaded files" + fileNames.toString());
        return "uploadStatusView";
    }

    @GetMapping("/myTrips")
    public ModelAndView myTrips(){
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
//
//    @GetMapping("/viewTrip")
//    public String getViewTripPage(){
//        return "/viewTrip";
//    }

    @RequestMapping(value = "/viewTrip", method = RequestMethod.POST)
    public ModelAndView viewTripPage(@RequestParam(name = "selectedTripId") Integer tripId) {
        ModelAndView modelAndView = new ModelAndView();

        Trip currentTrip = tripsService.getTripByTripId(tripId);

        currentTrip.setPictureList((List<Picture>) pictureService.getAllPicturesByTripId(tripId));

        modelAndView.addObject("selectedTrip",currentTrip);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String removeAd(@RequestParam(name = "selectedTripId") Integer tripId) {

        pictureService.removePictureByTripId(tripId);
        tripsService.removeTripByTripId(tripId);

        return "redirect:/myTrips";
    }


}
