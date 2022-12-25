package com.mustache.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //엔티티 어노테이션으로 객체 인식
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {

    @Id //Entity 대표값
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 생성
    private Long id;

    @Column //db에서 관리하는 테이블 단위에 연결
    private String title;

    @Column
    private String content;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    //cascade : 영속성 전이 설정 (게시글 삭제 시 연관관계 맺은 댓글도 모두 삭제되도록)
    private List<Reply> replies = new ArrayList<>();

    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void addReply(Reply reply) { // 양방향 매핑시 각 객체에 서로를 설정해 주어야
        reply.setArticle(this); // reply의 article 설정
        replies.add(reply); // article에 reply 추가
    }

    public void updateTitle(String title) {
        if (title != null) this.title = title;
    }

    public void updateContent(String content) {
        if (content != null) this.content = content;
    }

}
