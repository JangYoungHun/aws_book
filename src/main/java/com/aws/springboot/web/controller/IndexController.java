package com.aws.springboot.web.controller;

import com.aws.springboot.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService service;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", service.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/post/update/{id}")
    public String postsUpdate(@PathVariable("id")Long id,Model model){
        model.addAttribute("post",service.findById(id));
        return "posts-update";
    }


}
