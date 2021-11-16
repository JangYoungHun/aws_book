package com.aws.springboot.web.controller;

import com.aws.springboot.web.domain.posts.Posts;
import com.aws.springboot.web.domain.posts.PostsRepository;
import com.aws.springboot.web.dto.PostsSaveRequestDto;
import com.aws.springboot.web.service.PostsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//램덤 포트 사용설정. TestRestTemplate 주입을 위한 설정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostApiControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @LocalServerPort
    private int port;

    @AfterEach
    public void afterEach(){
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록(){
        //given
        String title = "test Title";
        String content = "test content";

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .athor("tester")
                .build();
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResponseEntity<Long> entity = testRestTemplate.postForEntity(url, dto, Long.class);


        //then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getAuthor()).isEqualTo("tester");
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}
