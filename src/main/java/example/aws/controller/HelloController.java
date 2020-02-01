package example.aws.controller;

import example.aws.model.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponse hello(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponse(name, amount);
    }
}
