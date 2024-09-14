package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 자동으로 id값을 1씩 증가
    private Long id; //primary key
    @ManyToOne //Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "article_id") //foreign key 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article; //해당 댓글의 부모 게시글
    @Column //아래 필드를 테이블의 속성으로 매핑
    private String nickname; //댓글 단 사람
    @Column
    private String body; //댓글 본문

    public static Comment createComment(CommentDto dto, Article article) {
        //예외 발생 코드 2가지
        //1. dto에 id가 존재하는 경우; 댓글 생성전에 id값이 있을 수 없기 때문, 엔티티의 id는 db가 자동생성
        if (dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        //2. 게시글이 일치하지 않는 경우;
        // dto에서(from request body) 가져온 부모 게시글 id와, 엔티티에서(from 요청url) 가져온 부모 게시글의 id가 다르면 안됨,
        // 즉 json데이터와 url요청 정보가 다르다는 뜻
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");

        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        //예외 발생 - 댓글 수정 요청시 url에 있는 id와 JSON 데이터(from request body)의 id가 다른 경우
        if (this.id != dto.getId()) //여기서 this는 target 댓글
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        //객체 갱신
        if (dto.getNickname() != null) //수정할 닉네임이 있다면
            this.nickname = dto.getNickname(); //내용 반영
        if (dto.getBody() != null) //수정할 본문 데이터가 있다면
            this.body = dto.getBody(); //내용 반영
    }
}
