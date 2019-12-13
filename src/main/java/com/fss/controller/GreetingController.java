package com.fss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController
{
    /**
     * Only Route enabled to test the Security configuration
     * @return
     */
    @GetMapping("/welcome")
    public String welcome()
    {
        return "Welcome - This secured route is accessed successful";
    }
}
