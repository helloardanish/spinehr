package com.ar.spinehr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ar.spinehr.serviceimpl.SeleniumServiceImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class MainController {

    @Autowired
    private SeleniumServiceImpl seleniumServiceImpl;
    
    // @GetMapping
    // public String home() {
    //     return "Welcome to the SpineHR application!";
    // }

    // http://localhost:8080/mark-in-out?username=%22ar%22&password=%22ar%22
    @GetMapping("/user-login")
    public String userLogin(@RequestParam String username, @RequestParam String password) {

        username = "E20240???";
        password = "E20240???";
        String category = "1";
        String action = "markOut";

        // check the ist time and mark in or out
        
        seleniumServiceImpl.doAction(username, password, category, action);
        // return "Login to the SpineHR application!";
        return "Operation completed!";
    }

    @GetMapping("/ok")
    public String ok(){
        return "OK, All Good!";
    }


    // http://localhost:8080/user-login?code=ar&action=markIn
    // http://localhost:8080/ar/in
    @GetMapping("/mark-in-out/{code}/{action}")
    public String makrInOut(@PathVariable String code, @PathVariable String action) {
        String username = "";
        String password = "";
        if(code.equals("ar")){
            username = "E20240???";
            password = "E20240???";
        }else if(code.equals("himanshug")){
            username = "E20240???";
            password = "E20240???";

            // Do for rest of the users
        }else{
            return "Invalid user";
        }

        // action = "testing";

        // TODO : Auto detect the action based on the time, if time between 9 to 5 mark in else mark out
        
        String category = "1";
        if(username.equals("") || password.equals("")){
            return "Invalid user";
        }
        boolean status = seleniumServiceImpl.doAction(username, password, category, action);
        log.info("Status : " + status);
        return status ? "Successfull operation!" : "Failed operation!";
    }
}
