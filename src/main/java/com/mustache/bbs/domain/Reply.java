package com.mustache.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Reply(Long id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public void updateAuthor(String author) {
        if (author != null) this.author = author;
    }

    public void updateContent(String content) {
        if (content != null) this.content = content;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
