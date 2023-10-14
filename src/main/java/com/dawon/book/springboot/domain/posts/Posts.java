package com.dawon.book.springboot.domain.posts;

import com.dawon.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // Entity클래스엔 절대 Setter메서드 만들지 않음
@NoArgsConstructor // 기본생성자 자동추가
@Entity // 테이블과 링크될 클래스
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성규칙. 꼭 GenerationType.IDENTITY 명시해아함함
    private Long id; // 웬만하면 PK는 Long으로 선언하기를 추천

    @Column(length = 500, nullable = false) // 굳이 선언 안해도 됨. 문자열의 경우 VARCHAR(255)RK RLQHSRKQT
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
