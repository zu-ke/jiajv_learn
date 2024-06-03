package com.zuke.zukeliving.commodity.controller;

import com.zuke.zukeliving.commodity.entity.CategoryEntity;
import com.zuke.zukeliving.commodity.service.CategoryService;
import com.zuke.zukeliving.commodity.vo.Catalog2Vo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;

    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {

        List<CategoryEntity> level1Categories = categoryService.getLevel1Categories();
        model.addAttribute("categories", level1Categories);

        //默认找的是 "classpath\templates\"+"index"+".html"
        return "index";
    }

    @GetMapping("/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

        Map<String, List<Catalog2Vo>> catalogJson = categoryService.getCatalogJson();
        return catalogJson;
    }
}
