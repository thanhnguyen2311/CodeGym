package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @GetMapping
    public String index(@RequestParam Optional<String> search, Model model, @PageableDefault(value = 3) Pageable pageable) {
        Page<Blog> blogs;
        if (search.isPresent()){
            blogs = blogService.findBlogByNameContains(search.get(), pageable);
        }else {
            blogs = blogService.findAll(pageable);
        }
        model.addAttribute("blogs", blogs);
        return "/index";
    }

    @GetMapping("/save")
    public String openSaveForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("dateDefault", LocalDate.now());
        return "/save";
    }

    @GetMapping("/save/{id}")
    public String openSaveForm(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id).get());
        return "/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        return "redirect:/blog";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.remove(id);
        return "redirect:/blog";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id).get());
        return "/detail";
    }
}
