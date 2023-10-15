package com.dawon.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
* JpaRepository<Entity클래스, PK타입> : DB Layer 접근자.
* - @Repository 추가할 필요 없음
* - 주의)Entity클래스와 Entity Repository는 함께 위치해야함(->도메인 패키지에서 함께 관리)
* */

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
