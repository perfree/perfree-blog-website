package org.perfree.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.perfree.model.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    void initTable();

    int add(Article article);

    void updateSlug(Article article);

    Article getById(String id);

    Article getBySlug(@Param("slug") String slug, @Param("type") String type);

    int update(Article article);

    List<Article> getList(@Param("page") int page, @Param("size") Integer size,
                          @Param("article") Article article);

    Long getTotal(Article article);

    void articleViewCountAdd(Long articleId);

}
