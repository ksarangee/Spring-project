package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Getter
public class Article {
    @Id                     //엔티티의 대푯값 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //자동 생성 기능 추가 (DB가 id 자동으로 생성함)
    private Long id;
    @Column                 //title 필드 선언, DB 테이블의 title열과 연결
    private String title;
    @Column                 //content 필드 선언, DB 테이블의 content열과 연결
    private String content;

    public void patch(Article article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }

    /*public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }*/
}
