package com.mustache.bbs.domain.dto;

import com.mustache.bbs.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ReplyDto {
    private Long id;
    private String author;
    private String content;
    public Reply toEntity() {
        return new Reply(id, author, content);
    }
}
