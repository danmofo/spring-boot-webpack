package com.dmoffat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author danielmoffat
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
