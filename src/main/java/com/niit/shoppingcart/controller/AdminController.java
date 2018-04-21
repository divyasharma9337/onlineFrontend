package com.niit.shoppingcart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;

@Controller
public class AdminController {
	private static Logger log = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private CategoryDAO categoryDAO;
	@SuppressWarnings("unused")
	@Autowired
	private Category category;
	@Autowired
	private SupplierDAO supplierDAO;
	@SuppressWarnings("unused")
	@Autowired
	private Supplier supplier;
	@Autowired
	HttpSession httpsession;
	@SuppressWarnings("unused")
	@Autowired
	private Product product;
	@Autowired
	private ProductDAO productDAO;
	
	@GetMapping("/managecategories")
	public ModelAndView admincClickedCategories()
	{
		ModelAndView mv = new ModelAndView("home");
		//1 - check whether user logged or not
	String LoggedInUserID= (String)httpsession.getAttribute("LoggedInUserID");
	if(LoggedInUserID==null)
	{
		//if not logged, "Please login to do this operation
		mv.addObject("errorMessage", "Please login to do this operation");
		return mv;
	}
		//2 - the already logged in
		//  check what is role of the user
	
	Boolean isAdmin =(Boolean)  httpsession.getAttribute("isAdmin");
		//if the role of user is not admin
		//"You are not authorized to do this operation
	 if(isAdmin ==null  || isAdmin==false)
	 {
		 mv.addObject("errorMessage", "You are not autheroized to do this operations.");
		 return mv;
	 }
		
		log.debug("starting of the method admincClickedCategories");
		
		mv.addObject("isAdminClickedManageCategories", true);
		//fetch all the categories again 
		List<Category> categories = categoryDAO.list();
		//and set to http session.
		httpsession.setAttribute("categories", categories);
		log.debug("ending of the method admincClickedCategories");
		return mv;
	}
	@GetMapping("/managesupplier")
	public ModelAndView adminClickedsuppliers()
	{
		
		log.debug("starting of the method admincClickedSupplier");
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("isAdminClickedManageSuppliers", true);
		
		List<Supplier> suppliers =  supplierDAO.list();
		httpsession.setAttribute("suppliers", suppliers);
		log.debug("ending of the method admincClickedSupplier");
		return mv;
	}
	@GetMapping("/manageproducts")
	public ModelAndView adminClickedproducts()
	{
		
		log.debug("starting of the method admincClickedProducts");
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("isAdminClickedManageProducts", true);
		//we suppsed to fetch all categories and suppliers
		//and set it to http sesion.
		 List<Category> categories = categoryDAO.list();
		 List<Supplier> suppliers = supplierDAO.list();
		 List<Product> products = productDAO.list();
		 
		 
		 httpsession.setAttribute("categories", categories);
		 httpsession.setAttribute("suppliers", suppliers);
		 httpsession.setAttribute("products", products);
		 log.debug("ending of the method admincClickedProducts");
		return mv;
	}


}