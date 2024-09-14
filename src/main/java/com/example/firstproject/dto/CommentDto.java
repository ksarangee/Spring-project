package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id; //댓글 id
    private Long articleId; //댓글 게시글 id
    private String nickname;
    private String body; //댓글 본문

    public static CommentDto createCommentDto(Comment comment) { //public static은 객체 생성 없이 호출 가능한 메서드라는 뜻
        return new CommentDto( //메서드 반환값이 댓글 DTO가 되도록 CommentDto 생성자 호출
                //getter() 메서드로 생성자 입력값 가져오기
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
