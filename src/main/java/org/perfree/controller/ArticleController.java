package org.perfree.controller;

import com.perfree.base.BaseController;
import com.perfree.commons.Constants;
import com.perfree.commons.Pager;
import com.perfree.commons.ResponseBean;
import com.perfree.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.perfree.model.Article;
import org.perfree.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @description 自定义文章Controller,用于插件仓库,主题仓库
 * @author Perfree
 * @date 2021/12/21 8:39
 */
@Controller
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    /**
     * 自定义添加文章(插件仓库,主题仓库)
     * @return String
     */
    @PostMapping("/admin/website/article/add")
    @ResponseBody
    @RequiresRoles(value={Constants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseBean add(@RequestBody @Valid Article article) {
        Article articleBySlug = articleService.getBySlug(article.getSlug(), "theme");
        if (articleBySlug != null && !articleBySlug.getId().equals(article.getId())){
            return ResponseBean.fail("访问地址别名重复", null);
        }
        User user = getUser();
        article.setUserId(user.getId());
        if (articleService.add(article) > 0) {
            return ResponseBean.success("添加成功", article);
        }
        return ResponseBean.fail("添加失败", null);
    }

    /**
     * 自定义更新文章(插件仓库,主题仓库)
     * @return String
     */
    @PostMapping("/admin/website/article/update")
    @ResponseBody
    @RequiresRoles(value={Constants.ROLE_ADMIN}, logical= Logical.OR)
    public ResponseBean update(@RequestBody @Valid Article article) {
        if (StringUtils.isBlank(article.getSlug())) {
            return ResponseBean.fail("访问地址别名不能为空", null);
        }
        Article articleBySlug = articleService.getBySlug(article.getSlug(), "theme");
        if (articleBySlug != null && !articleBySlug.getId().equals(article.getId())){
            return ResponseBean.fail("访问地址别名重复", null);
        }
        if (articleService.update(article) > 0) {
            return ResponseBean.success("更新成功", article);
        }
        return ResponseBean.fail("更新失败", null);
    }

    /**
     * 自定义文章管理列表数据(插件仓库,主题仓库)
     * @return Pager<Article>
     */
    @RequiresRoles(value={Constants.ROLE_ADMIN}, logical= Logical.OR)
    @PostMapping("/admin/website/article/list")
    @ResponseBody
    public Pager<Article> list(@RequestBody Pager<Article> pager) {
        User user = getUser();
        if ("contribute".equals(user.getRole().getCode())) {
            pager.getForm().setUserId(user.getId());
        }
        return articleService.list(pager);
    }

   /**
    * 自定义文章列表数据(插件仓库,主题仓库)
    * @param pager pager
    * @return Pager<org.perfree.model.Article>
    */
    @PostMapping("/website/article/list")
    @ResponseBody
    public Pager<Article> frontList(@RequestBody Pager<Article> pager) {
        return articleService.list(pager);
    }
}
