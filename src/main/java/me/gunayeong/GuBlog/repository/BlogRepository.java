package me.gunayeong.GuBlog.repository;

import me.gunayeong.GuBlog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository에서 제공하는 여러 메서드 사용 가능
public interface BlogRepository extends JpaRepository<Article, Long> {
}