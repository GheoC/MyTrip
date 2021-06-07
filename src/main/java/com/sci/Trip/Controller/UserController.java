package com.sci.Trip.Controller;

import com.sci.Trip.Model.User;
import com.sci.Trip.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {


    UserService userService;
    AuthenticationManager authenticationManager;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(){
        ModelAndView modelAndView=new ModelAndView();
        return modelAndView.addObject("users", userService.getAllUsers());
    }

    @GetMapping(value = "/register")
    public String showRegisterUserPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping(value = "/register")
    public String saveNewUser(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if(bindingResult.hasErrors()){
            return "register";
        }
        if(userService.findUser(user.getEmail())!=null){
            model.addAttribute("loginError", true);
        return "register";}

        userService.saveUser(user);

        authenticateUserAndSetSession(user);
        return "redirect:/";
    }

    private void authenticateUserAndSetSession(User user) {
        String username = user.getEmail();
        System.out.println(username);
        String password = user.getPassword();
        System.out.println(password);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, new ArrayList());
//        request.getSession();

//        token.setDetails(new WebAuthenticationDetails(request));
//        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

}
