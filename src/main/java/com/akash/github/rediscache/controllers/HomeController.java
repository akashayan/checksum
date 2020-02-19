package com.akash.github.rediscache.controllers;

import com.akash.github.rediscache.annotations.Loggable;
import com.akash.github.rediscache.dtos.BaseResponse;
import com.akash.github.rediscache.dtos.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class HomeController {

    @PostMapping(value = "/aspect")
    @Loggable
    public BaseResponse<String, String> testAspect(@RequestBody Person person) {
        System.out.println(person);
        return new BaseResponse<String, String>("200", "Success");
    }
}
