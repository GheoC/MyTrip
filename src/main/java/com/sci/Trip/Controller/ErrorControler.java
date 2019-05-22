package com.sci.Trip.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorControler {

    @GetMapping(name = "/error")
    public String getErrorPage(){
        return "error";
    }
}
