package com.mustache.bbs.controller;

import com.mustache.bbs.domain.Article;
import com.mustache.bbs.domain.dto.ArticleDto;
import com.mustache.bbs.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    public ArticleDto articleDto;

    @Autowired
    private ArticleRepository articleRepository;

    //1. 새 글 불러오기 : url로 들어오면 articles/new.mustache 출력
    @GetMapping(value = "/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    //2. 새 글 작성하기
    @PostMapping(value = "articles/posts")
    public String createArticle(ArticleDto form) { //ArticleDto 객체를 form 파라미터로
        //1) Dto를 entity로 변환
        Article articleEntity = form.toEntity();

        //2) Repository에게 Entity를 DB에 저장하게 한다
        Article saved = articleRepository.save(articleEntity);

        //3) 리다이렉트. 메서드 작동 종료 후 페이지 반환
        return "redirect:/articles/" + saved.getId();
    }
}
