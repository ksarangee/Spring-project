package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> { //CrudRespository는 JPA에서 제공하는 인터페이스로, 이를 상속해 엔티티를 관리

    @Override
    ArrayList<Article> findAll(); //Iterable -> ArrayList로 수정
}
