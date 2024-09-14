package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //서비스로 선언
public class CommentService {
    //서비스와 함께 협업할 댓글 리파지터리와 게시글 리파지터리 객체 주입
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        /*//1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        //2. 엔티티->DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>(); //조회한 댓글 엔티티 목록을 DTO 목록으로 변환하기 위해 빈 ArrayList 만들고 List<CommentDto> 타입의 dtos 변수에 저장
        for (int i = 0; i < comments.size(); i++) { //조회한 댓글 엔티티 수만큼 반복
            Comment c = comments.get(i); //조회한 댓글 엔티티 하나씩 가져오기
            CommentDto dto = CommentDto.createCommentDto(c); //엔티티를 DTO로 변환
            dtos.add(dto); //변환한 DTO를 dtos 리스트에 삽입
        }*/
        //3. 결과 반환
        return commentRepository.findByArticleId(articleId)//댓글 엔티티 목록 조회
                //가독성 위해 스트림 사용-> 컬렉션이나 리스트에 저장된 요소들을 하나씩 참조하며 반복 처리할 때 사용
                .stream()//댓글 엔티티 목록을 스트림로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) //각 엔티티(comment)를 DTO로 매핑; comment 엔티티 이름은 여기서 정한거임
                //.map() 괄호 안에 있는거 CommentDto::createCommentDto 이렇게 해도 됨
                .collect(Collectors.toList()); //스트림을 리스트로 변환

    }

    @Transactional //create 메서드는 DB내용을 바꾸기 때문에 실패할 경우를 대비해야함, 롤백 가능하게 @Transactional 어노테이션 추가
    public CommentDto create(Long articleId, CommentDto dto) {
        //1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) //부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! "+"대상 게시글이 없습니다.")); //없으면 에러 메시지
        //2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article); //댓글 dto와 게시글 엔티티(article)을 입력받아 댓글 엔티티(comment)를 만듬
        //3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment); //CommentRepository를 통해 comment 엔티티 db에 넣고, 결과를 created로 받아오기
        //4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created); //위에서 created에 안받고 그냥 comment를 인자값으로 보내도 됨
    }

    @Transactional //update 메서드는 DB의 내용을 변경하므라 실패할 경우를 대비해 데이터 롤백할 수 있게 @Transactional 넣기
    public CommentDto update(Long id, CommentDto dto) {
        //1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! " + "대상 댓글이 없습니다."));
        //2. 댓글 수정
        target.patch(dto);
        //3. DB로 갱신
        Comment updated = commentRepository.save(target); //DB 내용을 target으로 갱신 후 updated 변수로 받기
        //4. 댓글 엔티티를 DTO로 변환하고 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        //1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! "+"대상이 없습니다."));
        //2. DB에서 댓글 삭제
        commentRepository.delete(target);
        //3. 삭제한 댓글을 DTO로 변환하고 반환
        return CommentDto.createCommentDto(target);
    }
}
