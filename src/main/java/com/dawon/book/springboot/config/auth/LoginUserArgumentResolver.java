package com.dawon.book.springboot.config.auth;

import com.dawon.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    // supportsParameter : 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotation = parameter.getParameterAnnotations() != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        // @LoginUser어노테이션이 붙어 있고 파라미터 클래스 타입이 SessionUser.class인 경우 true반환
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    // resolveArgument : 파라미터에 전달할 객체를 생성. 여기 메서드에서는 세션에서 객체를 가져옴
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        return httpSession.getAttribute("user");
    }
}
