package com.mustache.bbs.controller;

import com.mustache.bbs.domain.Article;
import com.mustache.bbs.domain.Reply;
import com.mustache.bbs.domain.dto.ArticleDto;
import com.mustache.bbs.domain.dto.ReplyDto;
import com.mustache.bbs.repository.ArticleRepository;
import com.mustache.bbs.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    //@RequiredArgsConstructor로 DI받음
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;


    //1. 새 글 작성 페이지 불러오기 : url로 들어오면 articles/new.mustache 출력
    @GetMapping(value = "/new")
    public String newArticleForm() {
        return "articles/new";
    }

    //2. 새 글 작성하기
    @PostMapping(value = "/posts")
    public String createArticle(ArticleDto form) { //ArticleDto 객체를 form 파라미터로
        //1) Dto를 entity로 변환
        Article articleEntity = form.toEntity();

        //2) Repository에게 Entity를 DB에 저장하게 한다
        Article saved = articleRepository.save(articleEntity);

        //3) 리다이렉트. 메서드 작동 종료 후 페이지 반환
        return "redirect:/articles/" + saved.getId();
    }

    //3. URL 조회하기 : 엔티티를 article 변수로 만들어서 show 페이지에 출력
    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        //1) id로 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2) 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        //3) 보여줄 페이지 선택
        return "articles/show";

    }

    //4. 전체 조회하기 : 엔티티를 articleList로 만들어서 index 페이지에 출력
    @GetMapping("")
    public String getAll(Model model) {//articles로 요청을 받으면 getAll 메서드 작동

        //1) 모든 articles를 가져온다
        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();

        //2) 가져온 articles 리스트를 뷰로 전달 (모델을 뷰로 전달)
        model.addAttribute("articleList", articleEntityList);

        //3) 뷰 페이지 설정
        return "articles/list";
    }

    //5. 수정 요청 받기 : 엔티티를 article 변수로 만들어서 edit 페이지에 출력
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity); //수정할 articleEntity의 변수명 = article
        return "articles/edit";
    }

    //6. 데이터 수정하기 : edit.mustach에서 articles/update 요청 들어 오면 로직 진행
    // ** put을 폼이 받지 못해 patch 사용
    @PatchMapping("/update")
    public String update(ArticleDto dto) {
        //1) DTO 엔티티로 변환
        Article articleEntity = dto.toEntity();

        //2) ID가 DB에 있는지 찾기 아니면 null 반환
        Article targetEntity = articleRepository.findById(articleEntity.getId()).orElse(null);

        //3) DB에 기존 데이터가 있다면 값 변경
        if (targetEntity != null) {
            articleRepository.save(articleEntity);
        }

        //4) 수정 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }

    //7. 데이터 삭제하기 : html에서 delete요청 지원 x > deleteMapping 사용불가
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable Long id) {
        //1) 삭제대상 찾기
        Article deleteEntity = articleRepository.findById(id).orElse(null);

        //2) DB에 대상이 있다면 제거
        if (deleteEntity != null) {
            articleRepository.delete(deleteEntity);
        }

        //3) 결과 리다이렉트
        return "redirect:/articles";
    }

    //8. 댓글 추가하기
    @PostMapping("/{id}/reply")
    public String createReply(Model model, @PathVariable("id") Long id, ReplyDto replyDto) {
        Article article = articleRepository.findById(id).orElse(null);
        Reply reply = replyDto.toEntity();
        article.addReply(reply);
        replyRepository.save(reply);
        articleRepository.save(article);
        model.addAttribute("articles", article);
        model.addAttribute("replies", article.getReplies());
        return "reply/detail";
    }

    //9. 댓글 수정하기
    @PutMapping("/{articleId}/reply/{replyId}")
    public String editReply(@PathVariable("replyId") Long replyId, @RequestBody ReplyDto dto) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new RuntimeException("해당 댓글을 찾을 수 없습니다."));
        reply.updateAuthor(dto.getAuthor());
        reply.updateContent(dto.getContent());
        replyRepository.save(reply);
        return "reply/detail";
    }

    //10. 댓글 제거하기
    @DeleteMapping("/{articleId}/reply/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long replyId) {
        replyRepository.deleteById(replyId);
        return "reply/detail";
    }
}
