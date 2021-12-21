package org.perfree.controller;

import com.perfree.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController extends BaseController {

    @RequestMapping("/blog")
    public String plugin() {
        return view(currentThemePage() + "/blogList.html");
    }
}
