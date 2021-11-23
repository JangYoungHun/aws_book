package com.aws.springboot.web.controller;

import com.aws.springboot.web.dto.posts.PostsResponseDto;
import com.aws.springboot.web.dto.posts.PostsSaveRequestDto;
import com.aws.springboot.web.dto.posts.PostsUpdateRequestDto;
import com.aws.springboot.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService service;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto dto) {
        return service.save(dto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable("id")Long id, @RequestBody PostsUpdateRequestDto dto){
        return service.update(id, dto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id")Long id){
        return service.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable("id")Long id){
        service.delete(id);
        return id;
    }
}
