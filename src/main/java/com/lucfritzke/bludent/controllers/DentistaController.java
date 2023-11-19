package com.lucfritzke.bludent.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DentistaController {

    @GetMapping("/ola")
    public String returnNome(){

        return "Lucas";
    }
    
}
