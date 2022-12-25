package com.mustache.bbs.domain.dto;

import com.mustache.bbs.domain.Article;

public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='"+ title + '\''+ ", content='"+ content + '\''+ '}';
    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
