package com.dawon.book.springboot.domain.user;

import com.dawon.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    /*
    * @Enumerated(EnumType.STRING)
    * - jpa로 데이터베이스를 저장할 때 Enum값을 어떤 형태로 저장할지 결정함
    * */

    @Enumerated(EnumType.STRING) // jpa로 데이터베이스로 저장할때 문자열로 선언될 수 있도록 저장함. (기본형 int ->변경)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
