package com.ivr.router.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ivr/route")
@Slf4j
public class IvrRouteController {

    @GetMapping("/fetchData")
    public String fetchData() {
        return "This is a sample end point for fetchData";
    }

    @GetMapping("/refillRxData")
    public String refillRxData() {
        return "This is a sample end point for refillRxData";
    }

}
