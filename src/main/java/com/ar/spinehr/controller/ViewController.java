package com.ar.spinehr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ViewController {

    @GetMapping
    public String home() {
        log.info("Welcome to the SpineHR application!");
        return "login";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
