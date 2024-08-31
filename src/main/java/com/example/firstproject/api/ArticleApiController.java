package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //로그 찍을 수 있게 추가
@RestController //REST API용 컨트롤러
public class ArticleApiController {
    @Autowired //생성 객체를 가져와 연결
    private ArticleService articleService;

    //GET
    @GetMapping("/api/articles") //서비스를 통해 데이터 가져오기
    public List<Article> index() {
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable("id") Long id) { //id는 요청 url에서 가져 오므로 매개변수 앞에 @PathVariable 붙이기
        return articleService.show(id); //서비스가 조회 요청 처
    }

    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { //JSON 데이터를 받아 와햐 하니깐 @RequestBody 추가
        Article created = articleService.create(dto);
        return (created != null) ? //생성하면 정상 응답, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto); //서비스를 통해 게시글 수정
        return (updated != null) ? //수정되면 정상 응답, 안되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable("id") Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ? //deleted에 내용이 있으면 삭제되었다는 뜻, deleted에 내용이 없다면 삭제 실패했다는 뜻
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : //NO_CONTENT는 삭제되었다는 뜻
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test") //여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest (@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos); //서비스 호출, dtos는 dto 묶음
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } //transactionTest() 메서드 정의
}
