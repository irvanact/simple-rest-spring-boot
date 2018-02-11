package com.rest.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
public class HomeController {

    final Logger logger = LoggerFactory.getLogger(HomeController.class);
   @RequestMapping("/")
    public String  home() {
        return "hello";
    }
}
