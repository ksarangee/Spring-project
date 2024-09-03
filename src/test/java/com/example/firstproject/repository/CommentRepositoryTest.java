package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //리파지터리를 테스트하므로 해당 클래스를 JPA와 연동해 테스트
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") //이 어노테이션은 테스트 이름을 붙일 때 사용
    void findByArticleId() {
        /* Test Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            //1. 입력 데이터 준비
            Long articleId = 4L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고고"); //부모 게시글
            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅"); //댓글 객체 생성
            Comment b = new Comment(2L, article, "Kim", "Pursuit of Happyness");
            List<Comment> expected = Arrays.asList(a, b); //댓글 객체 합치기
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"4번 글의 모든 댓글을 출력!"); //여기서 메시지는 검증 실패했을 때 나타남
        }
        /* Test Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            //1. 입력 데이터 준비
            Long articleId = 1L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(1L, "가가가", "111"); //부모 게시글
            List<Comment> expected = Arrays.asList();
            //4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"1번 글은 댓글 없음"); //여기서 메시지는 검증 실패했을 때 나타남
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Test Case 1: "Park"의 모든 댓글 조회 */
        {
            //1. 입력 데이터 준비
            String nickname = "Park";
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //3. 예상 데이터
            //댓글 객체 생성 시 부모 객체는 각 필드에 따로 생성 (부모 게시글이 모두 다르기 때문)
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고고"), nickname, "굿 윌 헌팅");
            Comment b = new Comment(3L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨");
            Comment c = new Comment(5L, new Article(6L, "당신의 취미는?", "댓글 고고"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c); //댓글 객체 합치기
            //4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "\"Park\"의 모든 댓글 출력!");
        }
    }
}