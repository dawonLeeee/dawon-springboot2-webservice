package com.dawon.book.springboot.web;

import com.dawon.book.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class) // 테스트 진행시 JUnit에 내장된 실행자 외에 다른 실행자 실행시킴(SpringRunner); 스프링부트 테스트~JUnit사이 연결자.
@WebMvcTest(controllers = HelloControllerTest.class,
excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}) // Web어노테이션. @Controller등 사용가능
public class HelloControllerTest {

    /*excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)}
    * @WebMvcTest를 사용하기 때문에
    * (@WebMvcTest는 @ControllerAdvice, @Controller를 읽고 @Repository, @Service, @Component는 스캔대상이 아님.
    * --> SecurityConfig는 읽었지만 SecurityConfig를 생성하기 위해 필요한 CustomOAuth2UserService는 읽을 수 없어 에러가 발생한 것.)
    * --> CustomOAuth2UserService를 스캔하지 않기 때문에 스캔대상에서 SecurityConfig를 제거
    * */

    /*java.lang.IllegalArgumentException: JPA metamodel must not be empty!
    * @EnableJpaAuditing으로 인해 발생.
    * @EnableJpaAuditing을 사용하기 위해선 최소 하나의 @Entity클래스가 필요하지만 존재하지 않음
    * @EnableJpaAuditing과 @SprikngBootApplication이 함께 존재하다 보니 @WebMvcTest에서도 스캔하게 됨
    * --> @EnableJpaAuditing과 @SpringBootApplication둘을 분리
    * --> Application.java에서 @EnableJpaAuditingㅇ르 제거
    *
    * */

    @Autowired
    private MockMvc mvc; // 웹 API테스트시 사용. 이를 통해 HTTP GET, POST등에 대한 API테스트 가능

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))// MockMvc를 통해 /hello 주소로 HTTP GET요청을 함
                .andExpect(status().isOk())// HTTP Header의 status 검증. 200인지 여부 확인
                .andExpect(content().string(hello));// 응답 본문의 내용을 검증. hello가 맞는지 여부 확인
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is(name)))
                        .andExpect(jsonPath("$.amount", is(amount)));
        // jsonPath : json 응답값을 필드별로 검증할 수 있는 메서드
        // $를 기준으로 필드명을 명시.
        // 여기서는 name과  amount를 검증하니 $.name, $.amount로 검증
    }

}
