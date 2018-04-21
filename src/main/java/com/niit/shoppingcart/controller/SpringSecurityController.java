/*package com.niit.shoppingcart.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.niit.shoppingcart.dao.CartDAO;
import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.domain.Cart;
import com.niit.shoppingcart.domain.Category;
import com.niit.shoppingcart.domain.Product;
import com.niit.shoppingcart.domain.Supplier;
@Controller
public class SpringSecurityController {
	public static Logger log = LoggerFactory.getLogger(SpringSecurityController.class);
	@Autowired
	private CartDAO cartDAO;
	@Autowired
	private Cart cart;
	@Autowired
	private HttpSession httpsession;
	
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private Category category;
	@Autowired
	private SupplierDAO supplierDAO;
	@Autowired
	private Supplier supplier;
	@Autowired
	private Product product;
	
	@Autowired
	private ProductDAO productDAO;
	
	// authentication-failure-forward-url="/loginError"
	@RequestMapping(value = "/loginError", method = RequestMethod.GET)
	public String loginError(Model model) {
		log.debug("Starting of the method loginError");
		model.addAttribute("errorMessage", "Invalid Credentials.  Please try again.");
		//model.addAttribute("invalidCredentials", "true");
		log.debug("Ending of the method loginError");
		return "home";
	}
	// <security:access-denied-handler error-page="/accessDenied" />
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		log.debug("Starting of the method accessDenied");
		model.addAttribute("errorMessage", "You are not authorized to access this page");
		log.debug("Ending of the method accessDenied");
		return "home";
	}
	// <security:form-login authentication-success-forward-url="/success"/>
	
	 * @RequestMapping("/success") public ModelAndView successForwardURL() {
	 * log.debug("Starting of the method successForwardURL"); ModelAndView mv =
	 * new ModelAndView("home");
	 * 
	 * Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); String
	 * loggedInUserid = auth.getName(); Collection<GrantedAuthority> authorities
	 * = (Collection<GrantedAuthority>) auth.getAuthorities(); if
	 * (authorities.contains("A")) { mv.addObject("isAdmin", "true");
	 * log.debug("You are Admin"); } else { log.debug("You are not  Admin");
	 * mv.addObject("isAdmin", "false"); // cart = cartDAO.list(userID);
	 * mv.addObject("cart", cart); // Fetch the cart list based on user ID
	 * List<Cart> cartList = cartDAO.list(loggedInUserid);
	 * mv.addObject("cartList", cartList); mv.addObject("cartSize",
	 * cartList.size()); }
	 * 
	 * log.debug("Ending of the method successForwardURL"); return mv;
	 * 
	 * }
	 
*//**
 * If we are using spring-security, this method is going to execute after login
 * @param request
 * @param response
 * @return
 * @throws Exception
 *//*
	//@RequestMapping(value = "validate", method = RequestMethod.GET)
	@RequestMapping(value = "checkRole", method = RequestMethod.GET)
	public ModelAndView checkRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("starting of the method validate");
		ModelAndView mv = new ModelAndView("home");
		// session = request.getSession(true);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userID = auth.name();
		httpsession.setAttribute("loggedInUser", userID);
		if (request.isUserInRole("A")) {
			httpsession.setAttribute("isAdmin", true);
		} else {
			httpsession.setAttribute("isAdmin", false);
			
			httpsession.setAttribute("cart", cart);
			// Fetch the cart list based on user ID
			List<Cart> cartList = cartDAO.list(userID);
			httpsession.setAttribute("cartList", cartList);
			httpsession.setAttribute("cartSize", cartList.size());
			//session.setAttribute("totalAmount", cartDAO.getTotalAmount(userID));
			
		}
		log.debug("Ending of the method validate");
		return mv;
	}
	
	
	@RequestMapping("/secure_logout")
	public ModelAndView secureLogout()
	{
		//what you attach to session at the time login need to remove.
		
		//session.removeAttribute("loggedInUserID");
		httpsession.invalidate();
		
		ModelAndView mv = new ModelAndView("Home");
		
		//After logout also use should able to browse the categories and products
		//as we invalidated the session, need to load these data again.
		
		httpsession.setAttribute("category", category); // domain object names
		httpsession.setAttribute("product", product);
		httpsession.setAttribute("supplier", supplier);
		
		
		httpsession.setAttribute("categoryList", categoryDAO.list());
		
		httpsession.setAttribute("supplierList", supplierDAO.list());
		
		httpsession.setAttribute("productList", productDAO.list());
		
		
		//OR Simpley remove only one attribute from the session.
		
		//session.removeAttribute("loggedInUser"); // you no need to load categoriees,suppliers and products
	
		return mv;
		
	}
	
}
*/
