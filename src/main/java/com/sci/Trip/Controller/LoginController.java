package com.sci.Trip.Controller;

import com.sci.Trip.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;


    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "access-denied", method = RequestMethod.GET)
    public String getAccesDenied(){
        return "access-denied";
    }


//
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String getLoginForm(){
//        return "login";
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(@ModelAttribute(name = "loginForm") LoginForm loginForm, HttpSession session, Model model){
//        User users = userService.loginUser(loginForm.getEmail(),loginForm.getPassword());
//            if(users == null){
//                model.addAttribute("loginError", true);
//                return "login";
//            }
//        System.out.println(users.toString());
//        session.setAttribute("loggedInUser", users);
//        return "redirect:/main";
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET )
//    public String logout(HttpSession session){
//        session.removeAttribute("loggedInUser");
//        return "redirect:/main";
//    }
}


