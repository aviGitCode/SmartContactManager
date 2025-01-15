	package com.Smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Smart.Dao.UserRepository;
import com.Smart.Entities.User;
import com.Smart.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")

	public String home(Model model) {

		model.addAttribute("title", "Home -smart Contect Manager");
		return "home";
	}

	@RequestMapping("/about")

	public String about(Model model) {

		model.addAttribute("title", "about -smart Contect Manager");

		return "about";
	}

	@RequestMapping("/signup")

	public String singup(Model model) {

		model.addAttribute("title", "signup -smart Contect Manager");
		model.addAttribute("user", new User());

		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
	                           @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, 
	                           Model model, HttpSession session) {

	    try {
	        if (!agreement) {
	            System.out.println("You have not agreed to the terms and conditions!");
	            throw new Exception("You have not agreed to the terms and conditions!");
	        }

	        // Check for validation errors
	        if (result1.hasErrors()) {
	            System.out.println("Validation errors: " + result1.toString());
	            model.addAttribute("user", user);
	            return "signup";
	        }

	        // Set default user properties
	        user.setRole("USER_ROLE");
	        user.setEnabled(true);
	        user.setImageurl("avi.jpg");
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        
	        
	        System.out.println("AGREEMENT :" + agreement);
	        System.out.println("USER : " + user);

	        User result = this.userRepository.save(user);
	        model.addAttribute("user", new User());
	        session.setAttribute("message", new message("Successfully Registered!", "alert-success"));
	        return "signup";

	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("user", user);
	        session.setAttribute("message", new message("Something went wrong: " + e.getMessage(), "alert-danger"));
	        return "signup";
	    }
	}
	
	
	
	// handler for custome login page 
	
	@GetMapping("/signin")
	public String CustomLogin(Model model) {
		model.addAttribute("title", "signin page");
		
		
		return"/login";
	}
	
	
	
	
		
	

}
