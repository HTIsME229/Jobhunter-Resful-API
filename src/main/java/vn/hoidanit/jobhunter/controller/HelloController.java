package vn.hoidanit.jobhunter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.RestResponse;

@RestController
public class HelloController {

    @GetMapping("/")

    public String getHelloWorld() {
        return "Hello World";
    }
}
