package com.mustache.bbs.repository;

import com.mustache.bbs.domain.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    <S extends Article> S save(S entity);

    @Override
    Optional<Article> findById(Long aLong);

    @Override
    Iterable<Article> findAll();

    @Override
    void deleteById(Long aLong);
}
