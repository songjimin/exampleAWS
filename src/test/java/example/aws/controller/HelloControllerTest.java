package example.aws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.aws.model.HelloResponse;
import example.aws.model.PostMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void GET_문자열_리턴() throws Exception {

        String expectedResult = "Hello";
        mockMvc.perform(get("/hello"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(expectedResult));

    }
    @Test
    public void GET_DTO_리턴() throws Exception {

        String expectedNameValue = "Hello";
        int expectedAmountValue = 100;

        mockMvc.perform(get("/hello/dto")
                .param("name", expectedNameValue)
                .param("amount", String.valueOf(expectedAmountValue)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(expectedNameValue)))
                .andExpect(jsonPath("amount", is(expectedAmountValue)));
    }

    @Test
    public void POST_DTO_리턴() throws Exception {

        String testName = "POST";
        int testAge = 17;

        mockMvc.perform(post("/hello")
                .param("name", testName)
                .param("age",String.valueOf(testAge)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(testName)))
                .andExpect(jsonPath("age", is(testAge)));
    }

    @Test
    public void PUT_DTO_리턴() throws Exception {

        String testName = "PUT";
        int testAge = 17;

        PostMan testParam = PostMan.builder().name("PUT").age(17).build();

        mockMvc.perform(put("/hello")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testParam)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(testName)))
                .andExpect(jsonPath("age", is(testAge)));
    }

    @Test
    public void TEST() {

        String name = "test";
        int amount = 1000;
        //when
        HelloResponse dto = new HelloResponse(name, amount);
        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}

