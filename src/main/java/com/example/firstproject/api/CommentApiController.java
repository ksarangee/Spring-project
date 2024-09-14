package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.service.CommentService;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService; //컨트롤러가 서비스와 협업할 수 있도록 댓글 서비스 객체 주입

    //1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments") //@GetMapping으로 댓글 조회 요청 받기
    public ResponseEntity<List<CommentDto>> comments (@PathVariable Long articleId) {
        //DB에서 조회한 댓글 엔티티를 DTO로 변환하면 List<CommentDto>가 되고,
        // 여기에 응답코드를 같이 보내려면 ResponseEntity 클래스 활용.
        // 그래서 이 comments 메서드의 반환형은 ResponseEntity<List<CommentDto>>가 된다!

        //조회 작업 서비스한테 위임
        List<CommentDto> dtos = commentService.comments(articleId); //반환받은 값은 List<CommentDto> (댓글 DTO의 묶음) 타입의 dtos 변수에 저장
        //서비스한테 얻은 결과 클라이언트에 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos); //본문에 조회한 댓글 목록인 dtos 실어 보내기
    }

    //2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments") //댓글 생성 요청 받기
    //@RequestBody는 http요청 body에 실린 내용(json, xml, yaml)을 자바 객체로 변환해줌
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {//두번째 매개변수는 생성할 댓글 정보 받아오는 용
        //서비스에 작업 위임
        CommentDto createdDto = commentService.create(articleId, dto);
        //얻은 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto); //실패한 경우의 예외 처리는 스프링 부트가 담당
    }

    //3. 댓글 수정
    @PatchMapping("/api/comments/{id}") //여기서 id는 댓글의 id
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        //서비스한테 작업 위임
        CommentDto updateDto = commentService.update(id, dto); //updateDto에 수정한 댓글 데이터 넣기
        //얻은 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    //4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        //서비스한테 위임
        CommentDto deletedDto = commentService.delete(id);
        //얻은 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto); //body에 삭제한 댓글 데이터인 deletedDto를 실어보내기; body에 실어보내는 건 삭제된 게 내가 삭제하려던 댓글인지 확인용
    }

}
