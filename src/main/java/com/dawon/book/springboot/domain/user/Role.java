package com.dawon.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
// final이나 @NonNull인 필드 값만 파라미터로 받는 생성자. ex)Student std = new Student(1L);
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

    /*
    * 스프링 시큐리티에서는 항상 권한코드에 ROLE_이 있어야 함.
    * 그래서 코드별 키 값을 ROLE_GUEST, ROLE_USER등으로 저장
    * */
}
