package com.mustache.bbs.controller;

import com.mustache.bbs.domain.Article;
import com.mustache.bbs.domain.dto.ArticleDto;
import com.mustache.bbs.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    //3. URL 조회하기
    @GetMapping("/articles/{id}")
    public String read(@PathVariable Long id, Model model) {
        //1) id로 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2) 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        //3) 보여줄 페이지 선택
        return "articles/show";

    }

    //4. 전체 조회하기
    @GetMapping("/articles")
    public String getAll(Model model) {//articles로 요청을 받으면 getAll 메서드 작동

        //1) 모든 articles를 가져온다
        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();

        //2) 가져온 articles 리스트를 뷰로 전달 (모델을 뷰로 전달)
        model.addAttribute("articleList", articleEntityList);

        //3) 뷰 페이지 설정
        return "articles/index";
    }

}
