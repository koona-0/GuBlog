package me.gunayeong.GuBlog.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.gunayeong.GuBlog.domain.Article;
import me.gunayeong.GuBlog.dto.AddArticleRequest;
import me.gunayeong.GuBlog.dto.UpdateArticleRequest;
import me.gunayeong.GuBlog.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor        //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service    //빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 글 추가 메소드
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    //데이터베이스에 저장되어있는 글을 모두 가져오는 메소드
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //글의 ID를 이용해 글을 조회하는 메소드
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    //글의 ID를 이용해 글을 삭제하는 메소드
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    //글을 수정하는 메소드
    @Transactional  //트랜잭션 메소드 : 메소드를 하나의 트랜잭션으로 묶어 안전하게 값 변경
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    //게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}