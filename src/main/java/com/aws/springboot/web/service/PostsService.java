package com.aws.springboot.web.service;

import com.aws.springboot.web.domain.posts.Posts;
import com.aws.springboot.web.domain.posts.PostsRepository;
import com.aws.springboot.web.dto.posts.PostsListResponseDto;
import com.aws.springboot.web.dto.posts.PostsResponseDto;
import com.aws.springboot.web.dto.posts.PostsSaveRequestDto;
import com.aws.springboot.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {

    private final PostsRepository postsRepository;


    public Long save(PostsSaveRequestDto dto) {
        return postsRepository.save(dto.toEntity()).getId();
    }


    public PostsResponseDto findById(Long id){
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : "+ id));
        return new PostsResponseDto(post);
    }

    public Long update(Long id,PostsUpdateRequestDto dto){
        System.out.println(id);
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id : "+ id));
        post.update(dto.getTitle(), dto.getContent());
        return id;
    }


    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id : "+ id));
        postsRepository.delete(posts);
    }
}
