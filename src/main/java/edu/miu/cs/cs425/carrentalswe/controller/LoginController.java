package edu.miu.cs.cs425.carrentalswe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/eRental"})
public class LoginController {

    @GetMapping(value={"/public/login"})
    public String getLoginpage(){
        return "public/login";
    }

}
