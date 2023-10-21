package com.dawon.book.springboot.web;

import com.dawon.book.springboot.domain.posts.Posts;
import com.dawon.book.springboot.domain.posts.PostsRepository;
import com.dawon.book.springboot.web.dto.PostsSaveRequestDto;
import com.dawon.book.springboot.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    /*
    * @WebMvcTest를 사용하지 않음 : @WebMvcTest의 경우 JPA 기능이 작동하지 않음
    * --> JPA기능까지 한번에 테스트할 경우 @SpringBootTest와 TestRunTemplate을 사용하면 됨
    * */

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER") // 인증된 모의 사용자를 만들어 사용함
    // @SpringBootTest에서 MockMvc를 사용할 수 있도록 설정해야 사용가능함
    public void Posts_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("auther")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDTO = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDTO);

        // when
        mvc.perform(put(url) // mvc.perform : 생성된 MockMvc를 통해 api테스트함. 본문영역은 ObjectMapper를 통해 JSON으로 변환함.
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk());


        // then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        // responseEntity의 출력 결과
        // <
        // 200 OK OK,
        // 1,
        // [Content-Type:"application/json",
        // Transfer-Encoding:"chunked",
        // Date:"Fri, 14 Apr 2023 00:36:36 GMT",
        // Keep-Alive:"timeout=60",
        // Connection:"keep-alive"]
        // >

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
