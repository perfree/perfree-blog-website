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
public class PluginController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/plugin")
    public String plugin() {
        return view(currentThemePage() + "/pluginList.html");
    }

    @RequestMapping("/admin/website/plugin")
    @AdminMenu(name="插件仓库", groupId = "blogWebsite", seq = 2)
    public String pluginAdmin() {
        return "/blog-website-static/admin/plugin/plugin_list.html";
    }

    @RequestMapping("/admin/website/plugin/add")
    public String pluginAdd() {
        return "/blog-website-static/admin/plugin/plugin_create.html";
    }

    @RequestMapping("/admin/website/plugin/update/{id}")
    public String pluginUpdate(@PathVariable("id") String id, Model model) {
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return "/blog-website-static/admin/plugin/plugin_update.html";
    }

    @RequestMapping("/plugin/{slug}")
    public String articlePage(@PathVariable("slug") String slug, Model model, HttpServletRequest request) {
        if (slug.contains("-")) {
            String[] split = slug.split("-");
            slug = split[0];
            model.addAttribute("commentIndex", split[1]);
        }
        Article article = articleService.getBySlug(slug, "plugin");
        if (article != null) {
            articleService.cacheCount(article.getId().toString(), IpUtil.getIpAddr(request));
            model.addAttribute("articleId", article.getId());
            model.addAttribute("article", article);
            model.addAttribute(Constants.SEO_TITLE, StringUtils.isBlank(article.getTitle()) ? null : article.getTitle().trim());
            model.addAttribute(Constants.SEO_KEYWORD, StringUtils.isBlank(article.getMetaKeywords()) ? null : article.getMetaKeywords().trim());
            model.addAttribute(Constants.SEO_DESC, StringUtils.isBlank(article.getMetaDescription()) ? null : article.getMetaDescription().trim());
        }
        model.addAttribute("url", "/plugin/" + slug);
        return view(currentThemePage() + "/plugin.html");
    }
}
