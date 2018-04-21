package com.niit.shoppingcart.controller;

import java.sql.Timestamp;
import java.util.Collection;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.domain.User;


@Controller
public class UserController {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CategoryDAO categoryDAO;
	
	@Autowired
	SupplierDAO supplierDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
		
	
	@RequestMapping(value="login", method = RequestMethod.GET)
	public ModelAndView loginPage(Model m,HttpSession session,@RequestParam(value="error",required = false) String error,@RequestParam(value="logout",required = false) String logout){
		
		ModelAndView model = new ModelAndView();
		log.info("loginPage : Adding categoryList to session attribute");
		//session.setAttribute("categoryList", categoryDAO.getAllCategories());
		if(error!=null){
			model.addObject("error","Invalid Credentials (Username or Password)");
		}
		if(logout!=null){
			model.addObject("message", "Logged Out from Yourstyle");
		}
		log.info("loginPage : Redirecting to login page");
		model.addObject("categoryList", categoryDAO.getAllCategories());
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/login_attributes")
	public String login_session_attributes(HttpSession session,Model model,RedirectAttributes attributes) {
		log.info("login_attributes :  Fetching details from SecurityContextHolder --> email");
		String emailId = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("Email = "+emailId);
		log.info("login_attributes :  User details from Database using given email");
		User user = userDAO.getUserByEmailId(emailId);
		System.out.println("User = "+user.toString());
		log.info("login_attributes :  Checking if user details found or not Else redirect to Index page");
		if(user!=null){		
			log.info("login_attributes :  Fetching Authorities from Security Context");
				Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) SecurityContextHolder.getContext()
				.getAuthentication().getAuthorities();
				
				String role="ROLE_ADMIN";
				model.addAttribute("user", user);
				session.setAttribute("user", user);
				for (GrantedAuthority authority : authorities) 
				{
					log.info("login_attributes :  check for Admin Role");
				     if (authority.getAuthority().equals(role)) 
				     {
				    	 model.addAttribute("supplierList",supplierDAO.getAllSuppliers());
				    	 model.addAttribute("categoryList",categoryDAO.getAllCategories());
				    	 model.addAttribute("productList", productDAO.getAllProducts());
				    	 log.info("login_attributes :  Redirect to Admin home Page");
				    	 
				    	 return "redirect:/adminHome";
				     }
				     model.addAttribute("categoryList",categoryDAO.getAllCategories());
				     log.info("login_attributes :  Redirect to user home Page");
				     attributes.addFlashAttribute("products", productDAO.getAllProducts());
				     return "redirect:/home";
				}
				session.setAttribute("categoryList", categoryDAO.getAllCategories());
		}
		
		return "redirect:/";
	
	}
	
	@RequestMapping(value="loginfailed", method=RequestMethod.GET)
	public String loginerror(ModelMap model){
		log.info("loginerror :  Redirect to login with error");
		return "redirect:/login?error";
	}
	
	//Access Denied Handler
	@RequestMapping(value="accessDenied", method = RequestMethod.GET)
	public String showAccessDeniedPage(ModelMap model){
		log.info("showAccessDeniedPage :  Redirect to Access Denied Page");
		model.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "accessDenied";
	}
			
	@RequestMapping(value="signup", method = RequestMethod.GET)
	public String showSignUpPage(Model model){
		log.info("showSignUpPage : Set user detail in model -- Redirect to signup");
		model.addAttribute("user", new User());
		model.addAttribute("categoryList", categoryDAO.getAllCategories());
		return "signup";
	}
	
	@RequestMapping(value="savesignup", method = RequestMethod.POST)
	public String saveSignUpPage(@Valid @ModelAttribute("user") User user, BindingResult result,ModelMap model,@RequestParam("passwordRepeat")String passwordRepeat,RedirectAttributes attributes){
		
		if(result.hasErrors()){
			System.out.println(result.getAllErrors().toString());
			return "signup";
		}else{
		log.info("saveSignUpPage : Check if Password and confirm password are Same");
		if(!user.getPassword().equals(passwordRepeat)){
			model.addAttribute("errorMessage","Fields Password and Repeat Password do not match");
			return "signup";
		}
		
		log.info("saveSignUpPage : Fetching user detail based on email");
		User userExisting = userDAO.getUserByEmailId(user.getEmailId());
		
		log.info("saveSignUpPage : Check if email is already registered");
		
		if(userExisting != null){
			log.info("saveSignUpPage : This Email is already registered -- Show error");
			
			model.addAttribute("errorMessage","This Email is already registered");
			model.addAttribute("user", new User());
			return "login";
		}
		log.info("saveSignUpPage : Register the new user");
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		user.setCreatedBy("System");
		
		log.info("saveSignUpPage : Save details of User");
		userDAO.saveOrUpdate(user);
		
		//model.addAttribute("firstname", user.getFirstName()+" "+user.getLastName());
		  attributes.addFlashAttribute("products", productDAO.getAllProducts());
		return "redirect:/home";
		}
	}
}
