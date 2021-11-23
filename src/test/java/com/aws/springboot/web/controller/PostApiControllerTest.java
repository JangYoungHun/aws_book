package com.aws.springboot.web.controller;

import com.aws.springboot.web.domain.posts.Posts;
import com.aws.springboot.web.domain.posts.PostsRepository;
import com.aws.springboot.web.dto.posts.PostsSaveRequestDto;
import com.aws.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

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
    public void posts_등록(){
        //given
        String title = "test Title";
        String content = "test content";

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("tester")
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

    @Test
    public void posts_수정(){

        //given
        Posts posts = Posts.builder()
                .title("test title")
                .content("test content")
                .author("test author")
                .build();

        Posts savedPosts = postsRepository.save(posts);

        Long id = posts.getId();

        String updateTitle = "update title";
        String updateContent = "update content";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + id;

        HttpEntity<PostsUpdateRequestDto> httpEntity = new HttpEntity<>(requestDto);

        //when

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.PUT,
                httpEntity,
                Long.class );


        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> list = postsRepository.findAll();
        assertThat(list.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(list.get(0).getContent()).isEqualTo(updateContent);
    }

}
