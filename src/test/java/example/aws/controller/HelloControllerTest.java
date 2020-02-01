package example.aws.controller;

import example.aws.model.HelloResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void Hello_리턴() throws Exception {

        String expectedResult = "Hello";
        mockMvc.perform(get("/hello"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().string(expectedResult));

    }
    @Test
    public void Hello_DTO리턴() throws Exception {

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

