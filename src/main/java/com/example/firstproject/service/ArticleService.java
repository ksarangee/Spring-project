package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; //게시글 repository 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity(); //dto를 엔티티로 변환 후 article에 저장
        if (article.getId() != null) {
            return null; //article 객체에 id가 존재하면 null 반환, DB가 알아서 id를 생성하기 때문에 잘못된 post 요청이 됨
        }
        return articleRepository.save(article); //article을 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {
        //1. DTO -> 엔티티 변환
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        //2. 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            //400
            log.info("잘못된 요청 id: {}, article: {}", id, article.toString());
            return null; //응답은 컨트롤러가 하기 때문에 여기서는 그냥 null 반환
        }
        //4. 업데이트 및 정상 응답(200)하기
        target.patch(article); //기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); //수정 내용 DB에 최종 저장
        return updated; //응답은 컨트롤러가 하기 때문에 여기서는 수정 데이터만 반환
    }

    public Article delete(Long id) {
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리
        if (target == null) {
            return null; //응답은 컨트롤러가 하기 때문에 여기서는 그냥 null 반환
        }
        //3. 대상 삭제
        articleRepository.delete(target);
        return target; //db에서 삭제한 타깃을 컨트롤러에 반환
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음(list)을 엔티티 묶음(list)으로 변환
        List<Article> articleList = dtos.stream() //dtos를 엔티티 묶음으로 만들기 위해 스트림 문법 사용
                .map(dto -> dto.toEntity()) //map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해서 매핑하기
                .collect(Collectors.toList()); //위에서 매핑한 걸 리스트로 묶기, 최종 결과는 articleList에 저장됨
        //2. 엔티티 묶음을 DB에 저장
        articleList.stream() //articleList 스트림화하기
                .forEach(article -> articleRepository.save(article)); //article이 하나씩 올 때마다 articleRepository를 통해 DB에 저장
        /* 강제 예외 발생시키기
        articleRepository.findById(-1L) //id가 -1인 데이터 찾기 (오류)
                .orElseThrow(() -> new IllegalArgumentException("실패!")); //찾는 데이터가 없으면 예외 발생*/
        //4. 결과 값 반환
        return articleList;
    }
}
