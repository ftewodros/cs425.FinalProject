package edu.miu.cs.cs425.carrentalswe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping(value={"/","/eRental","eRental/secured/home","eRental/public/home"})
    public String getHomepage(){
        return "public/index";
    }

    @GetMapping(value={"eRental/public/about"})
    public String getAboutpage(){
        return "public/about";
    }

    @GetMapping(value={"eRental/public/contractor"})
    public String getBecomepage(){
        return "public/contractor";
    }
}
