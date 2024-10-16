package com.kt.edu.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 생성자
@Entity // DB가 해당 객체 인식 가능 (해당 클래스로 테이블을 만든다.)
@ToString // toString 함수
@NoArgsConstructor // 디폴트 생성자
@Getter
public class Article {
    @Id // PK 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 0,1,2,3.. 처럼 자동 생성 --> DB가 id를 자동생성하도록 변경됨.
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }


}
