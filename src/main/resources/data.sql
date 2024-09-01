--더미 데이터 생성
insert into article(title, content) values('가가가', '111');
insert into article(title, content) values('나나나', '222');
insert into article(title, content) values('가가가', '333');

--article 테이블에 데이터 추가
INSERT INTO article(title, content) VALUES('당신의 인생 영화는?', '댓글 고고');
INSERT INTO article(title, content) VALUES('당신의 소울 푸드는?', '댓글 고고');
INSERT INTO article(title, content) VALUES('당신의 취미는?', '댓글 고고');

--4번 게시글에 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Park', '굿 윌 헌팅');
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Kim', 'Pursuit of Happyness');

--5번 게시글에 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Park', '치킨');
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Kim', '샤브샤브');

--6번 게시글에 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Park', '조깅');
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Kim', '독서');




