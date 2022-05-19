package com.careerdevs.stockapiv1.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping
public class RootController {

    @GetMapping("/")
    public ResponseEntity<String> rootRoute () {
    //    return new ResponseEntity<>("Root Route", HttpStatus.OK);
        return ResponseEntity.ok("Root Route");
    }
}
