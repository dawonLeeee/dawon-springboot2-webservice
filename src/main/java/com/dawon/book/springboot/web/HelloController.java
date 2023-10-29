package com.dawon.book.springboot.web;

import com.dawon.book.springboot.web.dto.HelloResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 준다.
    // 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용되었다.
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDTO helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
        //  - 여기에선 외부에서 name (@RequestParam("name")) 이란 이름으로 넘긴 파라미터를 메서드 파라미터 name(String name)에 저장
        return new HelloResponseDTO(name, amount);
    }
}
