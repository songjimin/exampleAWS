package example.aws.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.aws.domain.posts.Posts;
import example.aws.domain.posts.PostsRepository;
import example.aws.web.dto.PostsSaveRequestDto;
import example.aws.web.dto.PutUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author("Author")
                                                            .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void Posts_조회() throws Exception {

        //given
        String title = "title";
        String author = "author";
        String content = "content";

        PostsSaveRequestDto postsSaveRequestDto =PostsSaveRequestDto.builder()
                                                                    .title(title)
                                                                    .author(author)
                                                                    .content(content)
                                                                    .build();

        String url = "http://localhost:" + port + "/api/v1/posts";
        //when

        MvcResult postReulst = mvc.perform(post((url))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(new ObjectMapper().writeValueAsString(postsSaveRequestDto)))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andReturn();

        Long postResultValue = new ObjectMapper().readValue(postReulst.getResponse().getContentAsString(), Long.class);

        MvcResult mvcResult = mvc.perform(get(url + "/" + postResultValue)
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andDo(print())
                                .andExpect(status().isOk()).andReturn();
        //then

        Posts result = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),Posts.class);
        assertThat(result.getId()).isEqualTo(postResultValue);
    }

    /**
     *
     * @throws Exception
     * 수정 테스트
     * 1.데이터 Insert (Post API Call)
     * 2.데이터 Update (Update API Call)
     * 3.수정된 데이터 Get (Get API Call)
     */
    @Test
    public void Posts_수정() throws Exception {

        //given
        String title = "title";
        String author = "author";
        String content = "content";

        PostsSaveRequestDto insertData =PostsSaveRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //when

        //데이터 넣기
        MvcResult mvcResult = mvc.perform(post((url))
                                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                .content(new ObjectMapper().writeValueAsString(insertData)))
                                                .andDo(print())
                                                .andExpect(status().isOk())
                                                .andReturn();


        //다시 given Update 요청 셋팅
        long updatedId = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Long.class);

        String title2 = "title2";
        String author2 = "author2";
        String content2 = "content2";

        PostsSaveRequestDto putUpdateRequestDto = PostsSaveRequestDto.builder()
                                                                .title(title2)
                                                                .content(content2)
                                                                .author(author2)
                                                                .build();

        //데이터 수정
        MvcResult updatedResult =  mvc.perform(put(url + "/" + updatedId)
                                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                    .content(new ObjectMapper().writeValueAsString(putUpdateRequestDto)))
                                                    .andDo(print())
                                                    .andExpect(status().isOk())
                                                    .andReturn();

        //데이터 수정된 데이터 가져오기
        MvcResult getResult = mvc.perform(get(url + "/" + updatedId)
                                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                                                .andDo(print())
                                                .andExpect(status().isOk()).andReturn();

        //then

        Posts result = new ObjectMapper().readValue(getResult.getResponse().getContentAsString(), Posts.class);

        assertThat(result.getId()).isEqualTo(updatedId);
        assertThat(result.getTitle()).isEqualTo(title2);
        assertThat(result.getContent()).isEqualTo(content2);
        assertThat(result.getAuthor()).isEqualTo(author2);

    }

    @Test
    public void Test_GET() throws Exception {

        String url = "http://localhost:" + port + "/events";

        mvc.perform(get(url+"/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void Test_POST() throws Exception {

        String url = "http://localhost:" + port + "/events";

        mvc.perform(post(url)
                .header("key", "abcd")
                .contentType(MediaType.APPLICATION_JSON_UTF8) //consume
                .accept(MediaType.APPLICATION_JSON_UTF8)) //accept
                .andDo(print())
                .andExpect(status().isOk());

    }
}
