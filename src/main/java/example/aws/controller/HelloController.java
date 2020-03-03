package example.aws.controller;

import example.aws.model.HelloResponse;
import example.aws.model.PostMan;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/hello")
    public PostMan postHello(@ModelAttribute PostMan postMan) {
        return postMan;
    }

    @PutMapping("hello")
    public PostMan putHello(@RequestBody PostMan postMan) {
        return postMan;
    }
}
