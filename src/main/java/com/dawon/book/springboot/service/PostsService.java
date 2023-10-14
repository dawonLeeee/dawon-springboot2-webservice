package com.dawon.book.springboot.service;

import com.dawon.book.springboot.domain.posts.Posts;
import com.dawon.book.springboot.domain.posts.PostsRepository;
import com.dawon.book.springboot.web.dto.PostsResponseDto;
import com.dawon.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDTO) {
        return postsRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto requestDto) {

        /* update기능에서 데이터베이스에 쿼리를 날리는 부분이 없음
        * --> 왜? jpa의 영속성 컨텍스트 때문 [dirty checking]
        *
        * ** 영속성 컨텍스트 : 엔티티를 영구저장하는 환경.
        *   - jpa의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태.
        *   - 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 지점에 해당 테이블에 변경분을 반영함
        *   ---> update쿼리를 날릴 필요가 없다
        * */
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }
}
