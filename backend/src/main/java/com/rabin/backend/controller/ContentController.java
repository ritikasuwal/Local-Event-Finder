package com.rabin.backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class ContentController {

    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password){
        return "login "+email;
    }

    @GetMapping("req/signup")
    public String signup(){
        return "signup";
    }

}
