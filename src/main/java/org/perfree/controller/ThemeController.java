package org.perfree.controller;

import com.perfree.base.BaseController;
import com.perfree.commons.Constants;
import com.perfree.commons.IpUtil;
import com.perfree.permission.AdminMenu;
import org.apache.commons.lang3.StringUtils;
import org.perfree.model.Article;
import org.perfree.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ThemeController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/theme")
    public String theme() {
        return view(currentThemePage() + "/themeList.html");
    }


    @RequestMapping("/admin/website/theme")
    @AdminMenu(name="主题仓库", groupId = "blogWebsite", seq = 1)
    public String themeAdmin() {
        return "/blog-website-static/admin/theme/theme_list.html";
    }

    @RequestMapping("/admin/website/theme/add")
    public String themeAdd() {
        return "/blog-website-static/admin/theme/theme_create.html";
    }

    @RequestMapping("/admin/website/theme/update/{id}")
    public String themeUpdate(@PathVariable("id") String id, Model model) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return "/blog-website-static/admin/theme/theme_update.html";
    }

    @RequestMapping("/theme/{slug}")
    public String articlePage(@PathVariable("slug") String slug,Model model,HttpServletRequest request) {
        if (slug.contains("-")) {
            String[] split = slug.split("-");
            slug = split[0];
            model.addAttribute("commentIndex", split[1]);
        }
        Article article = articleService.getBySlug(slug, "theme");
        if (article != null) {
            articleService.cacheCount(article.getId().toString(), IpUtil.getIpAddr(request));
            model.addAttribute("articleId", article.getId());
            model.addAttribute("article", article);
            model.addAttribute(Constants.SEO_TITLE, StringUtils.isBlank(article.getTitle()) ? null : article.getTitle().trim());
            model.addAttribute(Constants.SEO_KEYWORD, StringUtils.isBlank(article.getMetaKeywords()) ? null : article.getMetaKeywords().trim());
            model.addAttribute(Constants.SEO_DESC, StringUtils.isBlank(article.getMetaDescription()) ? null : article.getMetaDescription().trim());
        }
        model.addAttribute("url", "/theme/" + slug);
        return view(currentThemePage() + "/theme.html");
    }
}
