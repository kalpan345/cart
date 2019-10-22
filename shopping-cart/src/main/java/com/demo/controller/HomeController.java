package com.demo.controller;

import com.demo.model.Product;
import com.demo.service.ProductService;
import com.demo.util.Pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Optional;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;
    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/","/home"})
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Page<Product> products = productService.findAllProductsPageable(new PageRequest(evalPage, 5));
        Pager pager = new Pager(products);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
