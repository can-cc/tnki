package com.tnki.core;

import org.springframework.web.bind.annotation.*;

@RestController
public class CoreController {

    @RequestMapping(value="/hello", method= RequestMethod.GET)
    public String hello() {
        return "Hello world";
    }
}
