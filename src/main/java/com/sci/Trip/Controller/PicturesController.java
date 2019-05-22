package com.sci.Trip.Controller;

import com.sci.Trip.Repository.PicturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PicturesController {

    @Autowired
    PicturesRepository picturesRepository;


    @GetMapping("/pic")
    public ModelAndView getAllImages(){
        ModelAndView modelAndView=new ModelAndView();
        return modelAndView.addObject("picturesList", picturesRepository.findAll());
    }
}
