package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스를 스프링 부트와 연동해 테스트
class ArticleServiceTest {
    @Autowired //외부 객체를 주입하기 위함
    ArticleService articleService;
    @Test //해당 메서드가 테스트 코드임을 선언
    void index() {
        //1. 예상 데이터
        Article a = new Article(1L, "가가가", "111"); //예상 데이터 각각 객체로 저장, id는 Long 타입이라 접미사 L 붙여주기
        Article b = new Article(2L, "나나나", "222");
        Article c = new Article(3L, "가가가", "333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); //a, b, c를 Arrays.asList() 메서드로 정적 리스트로 만들어 일반 리스트 타입인 expected에 저장

        //2. 실제 데이터 획득
        List<Article> articles = articleService.index();

        //3. 예상 데이터와 실제 데이터 비교해 검증
        assertEquals(expected.toString(), articles.toString());
    }

    //show 메서드는 게시글 조회에 성공하는 경우와 실패하는 경우로 나눠서 테스트 케이스 작성
    @Test
    void show_success_when_correctId() {
        Long id = 1L;
        //1. 예상 데이터 (사용자가 id 1인 게시물의 조회를 요청했다 가정
        Article expected = new Article(id, "가가가", "111"); //예상 데이터 저장
        //2. 실제 데이터
        Article article = articleService.show(id); //실제 데이터 저장
        //3. 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_fail_when_wrongId() {
        Long id = -1L;
        //1. 예상 데이터 (사용자가 존재하지 않는 id인 -1을 조회한다 가정
        Article expected = null; //예상 데이터(null) 저장
        //2. 실제 데이터
        Article article = articleService.show(id); //실제 데이터 저장
        //3. 비교
        assertEquals(expected, article); //null은 toString() 메서드를 호출할 수 없기 때문에 첫 번째 두 번째 전달값은 그냥 expected와 article을 쓴다
    }

    @Test
    @Transactional
    void create_success() {
        //성공 테스트 케이스: title과 content만 있는 dto 입력할 경우
        //1. 예상 데이터
        String title = "라라라";
        String content = "444";
        ArticleForm dto = new ArticleForm(null, title, content); //dto 생성, id는 null로 냅두기 왜냐면 DB에서 자동 생성해주니깐
        Article expected = new Article(4L, title, content); //예상 데이터 저장
        //2. 실제 데이터
        Article article = articleService.create(dto);
        //3. 비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_fail() {
        //실패 테스트 케이스: id가 포함된 dto를 입력할 경우 (id는 사용자가 아니라 DB가 자동생성해주기 때문에 입력하면 안돼서 실패임)
        //1. 예상 데이터
        Long id = 4L;
        String title = "라라라";
        String content = "444";
        ArticleForm dto = new ArticleForm(id, title, content); //dto 생성
        Article expected = null; //예상 데이터 저장
        //2. 실제 데이터
        Article article = articleService.create(dto); //실제 생성 결과 저장
        //3. 비교
        assertEquals(expected, article);
    }
}