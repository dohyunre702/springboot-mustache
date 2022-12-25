package com.mustache.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //엔티티 어노테이션으로 객체 인식
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {

    @Id //Entity 대표값
    @GeneratedValue //자동 생성
    private Long id;

    @Column //db에서 관리하는 테이블 단위에 연결
    private String title;

    @Column
    private String content;

}
