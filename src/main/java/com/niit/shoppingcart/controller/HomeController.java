package com.niit.shoppingcart.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;


@Controller
public class HomeController {
	// http://localhost:8080/shoppingcarts
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	SupplierDAO supplierDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/")
	public String showIndexPage(HttpSession session,Model model){
		/*
		try {
			productDAO.indexProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		log.info("showIndexPage : Adding categoryList to session attribute");
		//session.setAttribute("categoryList", categoryDAO.getAllCategories());
		model.addAttribute("categoryList", categoryDAO.getAllCategories());
		log.info("showIndexPage :  Redirecting to index page");
		return "index";
	}
	

	@RequestMapping(value="home", method = RequestMethod.GET)
	public String showHomePage(Model m){
		log.info("showHomePage : Redirect to homepage");
		m.addAttribute("categoryList", categoryDAO.getAllCategories());
		m.addAttribute("products", productDAO.getAllProducts());
		return "home";
	}

	@RequestMapping(value="terms")
	public String showTermsOfUse(Model m){
		m.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "termsOfUse";
	}
	
	@RequestMapping(value="privacy")
	public String showPrivacyPolicy(Model m){
		m.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "privacypolicy";
	}
	
	@RequestMapping(value="contactus")
	public String showContactUsPage(Model m){
		m.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "contactUs";
	}
	
	@RequestMapping(value="adminHome")
	public String showAdminHomePage(Model m){
		m.addAttribute("categoryList", categoryDAO.getAllCategories());		
		return "adminHome";
	}
	
	@RequestMapping(value="about")
	public String showAboutUs(Model m){
		m.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "AboutUs";
	}
}
