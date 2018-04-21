package com.niit.shoppingcart.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.domain.Category;



@Controller
public class CategoryController {

	// we need to call CategoryDAO methods
	// get,save,update,delete,list

	// 1. inject the CategoryDAO and Category
private static Logger log = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@RequestMapping(value="categoryPage", method = RequestMethod.GET)
	public String showlistOfCategory(@ModelAttribute("category") Category category,  BindingResult result,  
            Model model, 
            RedirectAttributes redirectAttrs){
		
		 log.info("showlistOfCategory : Fetch all the categories");
		 List<Category> categoryList = categoryDAO.getAllCategories();
		 
		 log.info("showlistOfCategory : Adding categoryList to model");
	     model.addAttribute("categoryList", categoryList);
	     
		return "categoryPage";
	}
	
	@RequestMapping(value="saveCategory", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") Category category, BindingResult result,ModelMap model){
		
		log.info("saveCategory : Saving category details");
		category.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		category.setCreatedBy("System");
		categoryDAO.saveOrUpdate(category);
		
		log.info("saveCategory : Set category in model");
		model.addAttribute("category", category);
		return "redirect:/categoryPage";
	}
	
	@RequestMapping(value="/editcategory/{id}", method = RequestMethod.GET)
	public String editcategory(@PathVariable("id") int id,Model model,RedirectAttributes attributes){
		 
		log.info("editcategory : Edit category details -- fetch category by Id");
		attributes.addFlashAttribute("category", categoryDAO.getCategoryById(id));
		return "redirect:/categoryPage";
	}
	
	@RequestMapping(value="/removecategory/{id}", method = RequestMethod.GET)
	public String removeCategory(@PathVariable("id") int id, Model model,RedirectAttributes attributes){
		
		log.info("removeCategory : Delete category details -- remove category by Id");
		categoryDAO.deleteCategoryById(id);
		return "redirect:/categoryPage";
	}
	
	
}
