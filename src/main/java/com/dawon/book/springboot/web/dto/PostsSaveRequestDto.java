package com.dawon.book.springboot.web.dto;

import com.dawon.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    /*
    * Entity클래스와 유사한 형태임에도 Dto클래스를 추가로 만듬
    * - 절대로 Entity클래스를  request/response클래스로 사용하면 안됨
    *   ->> 왜? Entity클래스는 데이터베이스와 맞닿은 핵심클래스. 작은 변경사항에도 Entity클래스를 변경하는건 리스키함
    *   ->> 하지만 Dto클래스는 View와 관련있기 때문에 자주 변할 수 있음(ex-Controller에서 결과값으로 여러 테이블을 조인해줘야 할 경우 많음)
    * */

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
