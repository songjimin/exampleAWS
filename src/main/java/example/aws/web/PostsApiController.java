package example.aws.web;


import example.aws.domain.posts.Posts;
import example.aws.service.posts.PostsService;
import example.aws.web.dto.PostsSaveRequestDto;
import example.aws.web.dto.PutUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public Posts getPosts(@PathVariable Long id) {
        return postsService.getPostsList(id);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Posts upatePost(@PathVariable Long id, @RequestBody PostsSaveRequestDto postsSaveRequestDto) {
        return postsService.updatePosts(id, postsSaveRequestDto);
    }




    @GetMapping("/events/{number}")
    @ResponseBody
    public String test(@PathVariable String number) {
        return "test";
    }

    @PostMapping(value = "/events",
                consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
                 headers = "key=abcd")
    @ResponseBody
    public String test(WebRequest request, Locale locale, TimeZone timeZone, ZoneId zoneId) throws IOException {

        String keyValue =request.getHeader("key");

        return "postTest";
    }
}
