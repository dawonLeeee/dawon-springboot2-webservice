package com.dawon.book.springboot.web;

import com.dawon.book.springboot.config.auth.LoginUser;
import com.dawon.book.springboot.config.auth.dto.SessionUser;
import com.dawon.book.springboot.service.PostsService;
import com.dawon.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    // @LoginUser SessionUser user : 기존에 httpSession.getAttribute("user")로 가져오던 세션 정보값이 개선되었음
    // 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션정보를 가져올 수 있음
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null) model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id); // entity 반환
        model.addAttribute("post", dto);

        return "posts-update";
    }
}