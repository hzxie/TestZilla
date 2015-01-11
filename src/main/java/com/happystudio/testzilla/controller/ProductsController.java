package com.happystudio.testzilla.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 待测试产品的Controller.
 * @author Xie Haozhe
 */
@Controller
@RequestMapping(value = "/products")
public class ProductsController {
	/**
	 * 显示待测试的产品列表.
	 * @param request - HttpRequest对象
	 * @return 一个包含待测试的产品列表内容的ModelAndView对象
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView productsView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("products/products");
        return view;
    }
}
