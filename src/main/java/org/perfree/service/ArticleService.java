package org.perfree.service;

import com.perfree.commons.Pager;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.perfree.mapper.ArticleMapper;
import org.perfree.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    private static final CacheManager cacheManager = CacheManager.newInstance();

    /**
     * 添加文章
     * @param article article
     * @return int
     */
    public int add(Article article) {
        article.setViewCount(0L);
        article.setCommentCount(0L);
        article.setCreateTime(new Date());
        int result = articleMapper.add(article);
        if(StringUtils.isBlank(article.getSlug())) {
            article.setSlug(article.getId().toString());
        }
        articleMapper.updateSlug(article);
        return result;
    }

    public Article getById(String id) {
        return articleMapper.getById(id);
    }

    public Article getBySlug(String slug, String type) {
        return articleMapper.getBySlug(slug,type);
    }

    public int update(Article article) {
        article.setUpdateTime(new Date());
        return articleMapper.update(article);
    }

    public Pager<Article> list(Pager<Article> pager) {
        List<Article> articles = articleMapper.getList((pager.getPageIndex() - 1) * pager.getPageSize(),
                pager.getPageSize(),pager.getForm());
        pager.setTotal(articleMapper.getTotal(pager.getForm()));
        pager.setData(articles);
        pager.setCode(Pager.SUCCESS_CODE);
        return pager;
    }

    public void cacheCount(String articleId, String Ip) {
        com.perfree.model.Article article = getById(articleId);
        //查询缓存
        Ehcache cache = cacheManager.getEhcache("articleHits");
        Element element = cache.get(Ip+articleId+"_count");
        if(element==null && article != null){
            long count = article.getViewCount() == null?0:article.getViewCount();
            count++;
            cache.put(new Element(Ip+articleId+"_count",count));
            articleViewCountAdd(article.getId());
        }
    }

    public void articleViewCountAdd(Long articleId) {
        articleMapper.articleViewCountAdd(articleId);
    }
}
